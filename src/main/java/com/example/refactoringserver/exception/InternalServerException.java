package com.example.refactoringserver.exception;

public class InternalServerException extends RuntimeException {

    public InternalServerException(String message){
        super(message);
    }
}
