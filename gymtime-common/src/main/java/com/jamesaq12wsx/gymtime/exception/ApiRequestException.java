package com.jamesaq12wsx.gymtime.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiRequestException extends RuntimeException {

    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public ApiRequestException(HttpStatus status, String message){
        super(message);
        this.status = status;
    }

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
