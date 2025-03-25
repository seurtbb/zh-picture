package com.zh.zhpicturebackend.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class FileController {
    public void testFile(@RequestParam("file") MultipartFile multipartFile){
        String fileName = multipartFile.getOriginalFilename();

    }
}
