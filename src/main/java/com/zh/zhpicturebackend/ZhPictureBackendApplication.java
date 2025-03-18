package com.zh.zhpicturebackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@MapperScan("com.zh.zhpicturebackend.mapper")
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ZhPictureBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhPictureBackendApplication.class, args);
    }

}
