package com.example.refactoringserver.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    // todo 업로드 결과를 로컬 DB에 저장하도록 해주세요. 컬럼들: filename, url, ...
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {

        //S3에 저장될 파일 이름
        String fileName = dirName + "/" + UUID.randomUUID() + multipartFile.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType((multipartFile.getContentType()));

        //S3로 파일 업로드
        try(InputStream inputStream = multipartFile.getInputStream()){
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }catch (IOException e){
            throw new FileUploadException();
        }

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

}
