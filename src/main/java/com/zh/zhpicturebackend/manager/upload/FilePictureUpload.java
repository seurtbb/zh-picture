package com.zh.zhpicturebackend.manager.upload;

import cn.hutool.core.io.FileUtil;
import com.zh.zhpicturebackend.exception.ErrorCode;
import com.zh.zhpicturebackend.exception.ThrowUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @Author:zzh
 * @Date:2025/4/20 13:50
 * @Version:1.0
 * @Description: 文件上传
 */
@Service
public class FilePictureUpload extends PictureUploadTemplate {
    /**
     * 1 兆
     */
    private static final long ONE_M =  1024 * 1024L;

    private static final List<String> ALLOW_FORMAT_LIST = Arrays.asList("jpeg", "jpg", "png", "webp");
    /**
     * 校验输入源（本地文件或 URL）
     *
     * @param inputSource
     */
    @Override
    protected void validPicture(Object inputSource) {
        MultipartFile multipartFile = (MultipartFile) inputSource;
        ThrowUtils.throwIf(multipartFile == null, ErrorCode.PARAMS_ERROR, "文件为空");
        //1.校验文件大小
        ThrowUtils.throwIf(multipartFile.getSize() >2* ONE_M, ErrorCode.PARAMS_ERROR, "文件大小不能超过2M");
        //2.校验文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        //允许上传的文件后缀
        ThrowUtils.throwIf(!ALLOW_FORMAT_LIST.contains(fileSuffix), ErrorCode.PARAMS_ERROR, "不支持的文件类型");
    }

    /**
     * 获取输入源的原始文件名
     *
     * @param inputSource
     */
    @Override
    protected String getOriginFilename(Object inputSource) {
        MultipartFile multipartFile = (MultipartFile) inputSource;
        return multipartFile.getOriginalFilename();
    }

    /**
     * 处理输入源并生成本地临时文件
     *
     * @param inputSource
     * @param file
     */
    @Override
    protected void processFile(Object inputSource, File file) throws Exception {
        MultipartFile multipartFile = (MultipartFile) inputSource;
        multipartFile.transferTo(file);
    }
}
