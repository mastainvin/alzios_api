package com.alzios.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UploadFailException extends RuntimeException{
    private static final long serieVersionUID = 1L;

    public UploadFailException() {}
    public UploadFailException(String message) {
        super(message);
    }
    public UploadFailException(String message, Throwable cause){
        super(message, cause);
    }
}
