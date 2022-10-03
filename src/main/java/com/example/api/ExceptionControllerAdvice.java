package com.example.api;

import com.example.api.exception.UserExist;
import com.example.api.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResult userNotFound(UserNotFoundException ex){
        log.info("Exception :",ex);
        return new ErrorResult("UserNotFound",ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserExist.class)
    public ErrorResult userExist(UserExist e){
        log.info(e.getMessage());
        return new ErrorResult("UserExist",e.getMessage());
    }
}
