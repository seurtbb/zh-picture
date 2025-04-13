package com.zh.zhpicturebackend.manager;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.persistence.ImageInfo;
import com.zh.zhpicturebackend.config.CosClientConfig;
import com.zh.zhpicturebackend.exception.BusinessException;
import com.zh.zhpicturebackend.exception.ErrorCode;
import com.zh.zhpicturebackend.exception.ThrowUtils;
import com.zh.zhpicturebackend.model.dto.file.UploadPictureResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.File;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @author zzh
 */
@Service
@Slf4j
public class FileManager {
    /**
     * 1 兆
     */private static final long ONE_M =  1024 * 1024L;

    private static final List<String> ALLOW_FORMAT_LIST = Arrays.asList("jpeg", "jpg", "png", "webp");

    @Resource
    private CosClientConfig cosClientConfig;

    @Resource
    private CosManager cosManager;

    /**
     * 校验文件
     * @param multipartFile 文件
     */
    private void validateFile(MultipartFile multipartFile) {
        ThrowUtils.throwIf(multipartFile == null, ErrorCode.PARAMS_ERROR, "文件为空");
        //1.校验文件大小
        ThrowUtils.throwIf(multipartFile.getSize() >2* ONE_M, ErrorCode.PARAMS_ERROR, "文件大小不能超过2M");
        //2.校验文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        //允许上传的文件后缀
        ThrowUtils.throwIf(!ALLOW_FORMAT_LIST.contains(fileSuffix), ErrorCode.PARAMS_ERROR, "不支持的文件类型");
    }
    public UploadPictureResult uploadPicture(MultipartFile multipartFile, String uploadPathPrefix) {
        //校验图片
        validateFile(multipartFile);
        //图片上传路径
        String uuid=RandomUtil.randomString(16);
        String originalFileName = multipartFile.getOriginalFilename();
        //自己拼接文件的上传路径,而不是使用原始文件路径名称,可以增强安全性--->时间+uuid+文件后缀
        String uploadFileName=String.format("%s_%s_%s", DateUtil.formatDate(new Date()), uuid,FileUtil.getSuffix(originalFileName));
        String uploadPath=String.format("%s/%s",uploadPathPrefix,uploadFileName);
        File file = null;
        try {
            //上传文件
            file = File.createTempFile(uploadPath, null);
            multipartFile.transferTo(file);
            PutObjectResult putObjectResult = cosManager.putPictureObject(uploadPath, file);
            //获取图片信息对象
            ImageInfo imageInfo = putObjectResult.getCiUploadResult().getOriginalInfo().getImageInfo();
            //计算图片的宽高比
            int picWidth = imageInfo.getWidth();
            int picHeight = imageInfo.getHeight();
            double picScale=NumberUtil.round(picWidth *1.0 / picHeight,2).doubleValue();
            //封装返回结果
            //返回可访问的地址
            return UploadPictureResult.builder()
                    .url(cosClientConfig.getHost() + "/" + uploadPath)
                    .picName(FileUtil.mainName(originalFileName))
                    .picSize(FileUtil.size(file))
                    .picWidth(picWidth)
                    .picHeight(picHeight)
                    .picScale(picScale)
                    .picFormat(imageInfo.getFormat())
                    .build();
        } catch (Exception e) {
            log.error("图片上传失败到对象存储失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"文件上传失败");
        }
        finally {
            //临时文件清理
            this.deleteTempFile(file);
        }
    }




    /**
     * 临时删除文件
     * @param file 文件
     */
    private void deleteTempFile(File file) {
        if(file == null){
            return;
        }
        //删除文件
        boolean deleteResult = file.delete();
        if(!deleteResult){
            log.error("file delete error, filepath = {}", file.getAbsolutePath());
        }
    }
}