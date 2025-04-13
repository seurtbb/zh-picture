package com.zh.zhpicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.zhpicturebackend.model.dto.picture.PictureQueryRequest;
import com.zh.zhpicturebackend.model.dto.picture.PictureUploadRequest;
import com.zh.zhpicturebackend.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.zhpicturebackend.model.entity.User;
import com.zh.zhpicturebackend.model.vo.PictureVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
* @author zhouzhou
* @description 针对表【picture(图片)】的数据库操作Service
* @createDate 2025-03-25 09:02:52
*/
public interface PictureService extends IService<Picture> {
    /**
     * 上传图片
     *
     * @param multipartFile 上传的文件
     * @param pictureUploadRequest 文件请求id
     * @param loginUser 当前登录用户
     * @return 返回上传对象
     */
     PictureVO uploadPicture(MultipartFile multipartFile,
                                   PictureUploadRequest pictureUploadRequest,
                                   User loginUser);
    void validPicture(Picture picture);
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);
}
