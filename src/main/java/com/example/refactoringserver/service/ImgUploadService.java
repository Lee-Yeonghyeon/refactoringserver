package com.example.refactoringserver.service;

import com.example.refactoringserver.model.entity.FileInfoEntity;
import com.example.refactoringserver.model.result.RestResult;
import com.example.refactoringserver.repo.FileInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImgUploadService {

    private final S3Uploader s3Uploader;
    private final FileInfoRepository fileInfoRepository;
    private final ModelMapper modelMapper;

    public List<RestResult> findAllFile(){
        List<FileInfoEntity> fileList = fileInfoRepository.findAll();
        List<RestResult> fileResult = Arrays.asList(modelMapper.map(fileList, RestResult[].class));
        return fileResult;
    }

    public RestResult upload(MultipartFile multipartFile) throws IOException {
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

        final FileInfoEntity fileInfoEntity = fileInfoRepository.save(fileInfo);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("upload", fileInfoEntity);
        return new RestResult(data);
    }
}
