package com.example.yoloq.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class LoadingException extends RuntimeException{
    public LoadingException(String message) {
        super(message);
    }
}
