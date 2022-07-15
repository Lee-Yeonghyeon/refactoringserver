package com.example.refactoringserver.controller;

import com.example.refactoringserver.model.result.RestResult;
import com.example.refactoringserver.service.ImgUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImgUploadController {

    private final ImgUploadService imgUploadService;

    // todo 1. exception handling 추가요청 : Exception 이 발생할 확률이 있다면 catch 해서 처리해야함.
    // todo 2. null 리턴은 금물.
    // todo 3. layered architecture 적용 필요.
    // todo 4. FileInfoEntity --> FileInfoResult 로 변경 후 리턴. (modelMapper 를 활용.)
    // todo 5. log ?? 필요.
    // todo 6. api spec 을 정규화해야한다.

    @GetMapping("/findAll")
    public List<RestResult> findAllFile() {
        log.info("/findAllFile is called");
        return imgUploadService.findAllFile();
    }

    @PostMapping("/saveImage")
    public RestResult upload(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        // todo 요청/응답(일부) 에 대한 로그 필요.
        log.info("/saveImage filename: {}", multipartFile.getOriginalFilename());
        return imgUploadService.upload(multipartFile);
    }

}
