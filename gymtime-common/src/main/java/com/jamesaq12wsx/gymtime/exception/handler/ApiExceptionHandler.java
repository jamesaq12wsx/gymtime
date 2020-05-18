package com.jamesaq12wsx.gymtime.exception;

import com.jamesaq12wsx.gymtime.model.ApiResponseBuilder;
import com.jamesaq12wsx.gymtime.model.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiRequestException.class)
    @ResponseBody
    public ResponseEntity<Object> handleApiRequestException(HttpServletRequest request, ApiRequestException e) {

//        ApiResponse apiResponse = new ApiResponse(
//                false,
//                e.getMessage(),
//                null
//        );

        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException e) {

        ApiResponse apiResponse = new ApiResponse(
                false,
                e.getMessage(),
                null
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Object> handleBadCredentialException(AuthenticationException e) {

        ApiResponse apiResponse = new ApiResponse(
                false,
                e.getMessage(),
                null
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        ex.printStackTrace();

        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((err) -> {
            String fieldName = ((FieldError)err).getField();
            String errMessage = err.getDefaultMessage();
            errors.put(fieldName, errMessage);
        });

        ApiResponse response = ApiResponseBuilder.createFailedResponse("Request body valid error", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
