package com.example.refactoringserver.exception;

import com.example.refactoringserver.model.result.RestError;
import com.example.refactoringserver.telegram.RefactoryBot;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    //텔레그램에 메세지를 보낼 bot
    private final RefactoryBot refactoryBot;
    public ExceptionAdvice(RefactoryBot refactoryBot) {
        this.refactoryBot = refactoryBot;
    }

    /*
    4XX: Client error responses
    */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public RestError BadRequestExceptionHandler(BadRequestException e){
        refactoryBot.sendMessage(e.toString());
        return new RestError("bad request", e.getMessage());
    }

    /*
    5XX: Server error responses
    */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerException.class)
    public RestError InternalServerExceptionHandler(InternalServerException e){
        refactoryBot.sendMessage(e.toString());
        return new RestError("internal server error", e.getMessage());
    }

}
