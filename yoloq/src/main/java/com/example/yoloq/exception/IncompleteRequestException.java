package com.example.yoloq.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IncompleteRequestException extends RuntimeException {
    public IncompleteRequestException(String message) { super(message);}
}
