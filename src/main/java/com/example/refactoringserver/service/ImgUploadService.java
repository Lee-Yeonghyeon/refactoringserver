package com.example.refactoringserver.service;

import com.example.refactoringserver.exception.BadRequestException;
import com.example.refactoringserver.model.entity.FileInfoEntity;
import com.example.refactoringserver.model.result.FileInfoDto;
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
        List<FileInfoDto> fileInfoDtoList = Arrays.asList(modelMapper.map(fileList, FileInfoDto[].class));

        Map<String, Object> data = new LinkedHashMap<>();
        Arrays.asList(data.put("findAll", fileInfoDtoList));
        return Arrays.asList(new RestResult(data));
    }

    public RestResult upload(MultipartFile multipartFile) {
        try{
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

            FileInfoEntity fileInfoEntity = fileInfoRepository.save(fileInfo);
            FileInfoDto fileInfoDto = modelMapper.map(fileInfoEntity, FileInfoDto.class);

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("upload", fileInfoDto);
            return new RestResult(data);

        } catch(IOException e) {
            throw new BadRequestException("지원하지 않는 파일입니다.");
        } catch (NullPointerException e){
            throw new BadRequestException("파일이 존재하지 않습니다.");
        }

    }
}
