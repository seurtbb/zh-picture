package com.zh.zhpicturebackend.RedisSerializable;

import com.zh.zhpicturebackend.model.entity.Picture;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;


/**
 * @Author:zzh
 * @Date:2025/4/21 19:28
 * @Version:1.0
 * @Description:
 */
@SpringBootTest
public class RedisSerializable {
    @Resource
    private RedisTemplate redisTemplate;
    @Test
    void NoSerializable() {
        // 创建一个对象
        Picture picture = new Picture();
        // 设置对象的属性
        picture.setName("Image");
        redisTemplate.opsForValue().set("picture2", picture);


    }
}
