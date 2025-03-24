package com.zh.zhpicturebackend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author zzh
 * @Date 2025/3/24 13:28
 * @Version 1.0
 * @Description TODO
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoCheck {
    //必须具有的某个角色
    String mustRole() default "";
}
