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

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImgUploadController {

    private final ImgUploadService imgUploadService;

    // todo 1. exception handling 추가요청 : Exception 이 발생할 확률이 있다면 catch 해서 처리해야함.
    // todo 1-1. checked exception --> runtime exception로 변경 후 handling
    // todo 1-2. http 상태코드 설정하기(200, 400, 500 etc..)

    // todo 4. FileInfoEntity --> FileInfoResult 로 변경 후 리턴. (modelMapper 를 활용.)
    // todo 4-1. 호출 결과 RestResult로 반환하기 전, FileInfoResult로 변환하는 과정 필요함

    @GetMapping("/findAll")
    public List<RestResult> findAllFile() {
        log.info("/findAllFile is called");
        return imgUploadService.findAllFile();
    }

    @PostMapping("/saveImage")
    public RestResult upload(@RequestParam("images") MultipartFile multipartFile) {
        log.info("/saveImage filename: {}", multipartFile.getOriginalFilename());
        return imgUploadService.upload(multipartFile);
    }

}
