package com.zh.zhpicturebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.zhpicturebackend.model.entity.Picture;
import com.zh.zhpicturebackend.service.PictureService;
import com.zh.zhpicturebackend.mapper.PictureMapper;
import org.springframework.stereotype.Service;

/**
* @author zhouzhou
* @description 针对表【picture(图片)】的数据库操作Service实现
* @createDate 2025-03-25 09:02:52
*/
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture>
    implements PictureService{

}




