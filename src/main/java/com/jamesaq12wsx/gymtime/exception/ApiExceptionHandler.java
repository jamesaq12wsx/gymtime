package com.jamesaq12wsx.gymtime.exception;

import com.jamesaq12wsx.gymtime.model.payload.ApiResponse;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiRequestException.class)
    @ResponseBody
    public ResponseEntity<Object> handleApiRequestException(HttpServletRequest request, ApiRequestException e){

        ApiResponse apiResponse = new ApiResponse(
                false,
                e.getMessage(),
                null
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException e){

        ApiResponse apiResponse = new ApiResponse(
                false,
                e.getMessage(),
                null
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Object> handleBadCredentialException(AuthenticationException e){

        ApiResponse apiResponse = new ApiResponse(
                false,
                e.getMessage(),
                null
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);

    }

//    @ExceptionHandler(value = Exception.class)
//    public ResponseEntity<Object> handleApiInternalException(HttpServletRequest request, Exception e){
//
//        e.printStackTrace();
//
//        HttpStatus status = getStatus(request);
//
//        ApiException apiException = new ApiException(
//                e.getMessage(),
//                status,
//                LocalDateTime.now()
//        );
//
//        return new ResponseEntity<>(apiException, status);
//    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
