package com.example.refactoringserver.controller;

import com.example.refactoringserver.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class ImgUploadController {

    private final S3Uploader s3Uploader;

    // todo 결과를 json 으로 리턴할 수 있도록 변경해주세요.
    @PostMapping("/images")
    public String upload(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        s3Uploader.upload(multipartFile, "static");
        return "test";
    }
}
