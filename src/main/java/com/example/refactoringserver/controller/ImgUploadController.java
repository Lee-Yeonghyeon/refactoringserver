package com.example.refactoringserver.controller;

import com.example.refactoringserver.entity.FileInfoEntity;
import com.example.refactoringserver.repo.FileInfoRepository;
import com.example.refactoringserver.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImgUploadController {

    private final S3Uploader s3Uploader;
    private final FileInfoRepository fileInfoRepository;

    @GetMapping("/findAll")
    public List<FileInfoEntity> findAllMember(){
        return fileInfoRepository.findAll();
    }

    @PostMapping("/saveImage")
    public FileInfoEntity upload(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        String s3Url = s3Uploader.upload(multipartFile);

        BufferedImage image = ImageIO.read(multipartFile.getInputStream());

        final FileInfoEntity fileInfo = FileInfoEntity.builder()
                .fileName(multipartFile.getOriginalFilename())
                .format(multipartFile.getContentType())
                .size(multipartFile.getSize())
                .width(image.getWidth())
                .height(image.getHeight())
                .url(s3Url)
                .build();

        return fileInfoRepository.save(fileInfo);
    }
}
