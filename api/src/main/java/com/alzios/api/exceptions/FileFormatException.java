package com.alzios.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class FileFormatException extends RuntimeException{
    private static final long serieVersionUID = 1L;

    public FileFormatException() {}
    public FileFormatException(String message) {
        super(message);
    }
    public FileFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
