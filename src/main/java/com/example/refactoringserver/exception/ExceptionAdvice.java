package com.example.refactoringserver.exception;

import com.example.refactoringserver.model.result.RestError;
import com.example.refactoringserver.telegram.RefactoryBot;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;

@RestControllerAdvice
public class ExceptionAdvice {

    //텔레그램에 메세지를 보낼 bot
    private final RefactoryBot refactoryBot;
    public ExceptionAdvice(RefactoryBot refactoryBot) {
        this.refactoryBot = refactoryBot;
    }

    @ExceptionHandler(IOException.class)
    public RestError iOExceptionHandler(IOException e){
        refactoryBot.sendMessage(e.toString());
        return new RestError("ioE", "입출력 예외");
    }

    @ExceptionHandler(NullPointerException.class)
    public RestError nullPointerExceptionHandler(NullPointerException e){
        refactoryBot.sendMessage(e.toString());
        return new RestError("nullE", "null 객체 참조 예외");
    }

    @ExceptionHandler(Exception.class)
    public RestError ExceptionHandler(Exception e){
        refactoryBot.sendMessage(e.toString());
        return new RestError("exception", "예외");
    }

    @ExceptionHandler(TelegramApiException.class)
    public RestError TelegramApiExceptionHandler(TelegramApiException e){
        refactoryBot.sendMessage(e.getMessage());
        return new RestError("telegramE", "텔레그램 메세지 전송 예외");
    }

    @ExceptionHandler(TelegramApiRequestException.class)
    public RestError TelegramApiReqExceptionHandler(TelegramApiRequestException e){
        refactoryBot.sendMessage(e.getMessage());
        return new RestError("telegramE", "텔레그램 api 예외");
    }
}
