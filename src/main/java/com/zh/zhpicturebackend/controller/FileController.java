package com.zh.zhpicturebackend.controller;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.utils.IOUtils;
import com.zh.zhpicturebackend.annotation.AutoCheck;
import com.zh.zhpicturebackend.common.BaseResponse;
import com.zh.zhpicturebackend.common.ResultUtils;
import com.zh.zhpicturebackend.constant.UserConstant;
import com.zh.zhpicturebackend.exception.BusinessException;
import com.zh.zhpicturebackend.exception.ErrorCode;
import com.zh.zhpicturebackend.manager.CosManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private CosManager cosManager;

    /**
     * 测试文件上传
     *
     * @param multipartFile 上传的文件
     * @return
     */
    @AutoCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/test/upload")
    public BaseResponse<String> testUploadFile(@RequestPart("file") MultipartFile multipartFile) {
        // 文件目录
        String filepath = String.format("/test/%s", multipartFile.getOriginalFilename());
        File file = null;
        try {
            // 上传文件
            file = File.createTempFile(filepath, null);
            multipartFile.transferTo(file);
            cosManager.putObject(filepath, file);
            // 返回可访问地址
            return ResultUtils.success(filepath);
        } catch (Exception e) {
            log.error("file upload error, filepath = {}", filepath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            if (file != null) {
                // 删除临时文件
                boolean delete = file.delete();
                if (!delete) {
                    log.error("file delete error, filepath = {}", filepath);
                }
            }
        }
    }

    @AutoCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/test/downlad")
    public void testDownloadFile(@RequestParam("filepath") String filepath, HttpServletResponse response) throws IOException {
        COSObjectInputStream cosObjectInputStream=null;
        try {
            // 下载文件
            COSObject cosObject = cosManager.getObject(filepath);
             cosObjectInputStream = cosObject.getObjectContent();
            byte[] bytes = IOUtils.toByteArray(cosObjectInputStream);
            //设置响应头
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + filepath);
            //写入响应
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("file upload error, filepath = {}", filepath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "下载异常");
        }finally {
            if (cosObjectInputStream!=null){
                cosObjectInputStream.close();
            }
        }

    }
}


