package com.example.refactoringserver.controller;

import com.example.refactoringserver.model.entity.FileInfoEntity;
import com.example.refactoringserver.repo.FileInfoRepository;
import com.example.refactoringserver.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    // todo 1. exception handling 추가요청 : Exception 이 발생할 확률이 있다면 catch 해서 처리해야함.
    // todo 2. null 리턴은 금물.
    // todo 3. layered architecture 적용 필요.
    // todo 4. FileInfoEntity --> FileInfoResult 로 변경 후 리턴. (modelMapper 를 활용.)
    // todo 5. log ?? 필요.
    // todo 6. api spec 을 정규화해야한다.

    @GetMapping("/findAll")
    public List<FileInfoEntity> findAllMember() {
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

            // todo 요청/응답(일부) 에 대한 로그 필요.
            return fileInfoRepository.save(fileInfo);
    }
}
