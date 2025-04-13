package com.zh.zhpicturebackend.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author:zzh
 * @Date:2025/4/13 13:38
 * @Version:1.0
 * @Description: 图片标签分类视图
 */
@Data
public class PictureTagCategory {
   private List<String> tagList;
    private List<String> categoryList;
}
