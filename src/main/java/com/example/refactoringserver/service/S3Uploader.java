package com.example.refactoringserver.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.refactoringserver.exception.BadRequestException;
import com.example.refactoringserver.telegram.RefactoryBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    private final RefactoryBot refactoryBot;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    public String upload(MultipartFile multipartFile) {

        //S3에 저장될 파일 이름
        String fileName = UUID.randomUUID() + multipartFile.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType((multipartFile.getContentType()));
        objectMetadata.setContentLength(multipartFile.getSize());

        //S3로 파일 업로드
        try(InputStream inputStream = multipartFile.getInputStream()){
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch(IOException e) {
            throw new BadRequestException("지원하지 않는 파일입니다.");
        } catch (NullPointerException e){
            throw new BadRequestException("파일이 존재하지 않습니다.");
        }

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

}
