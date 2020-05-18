package com.jamesaq12wsx.gymtime.exception.handler;

import com.jamesaq12wsx.gymtime.util.ThrowableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    /**
     * Catch all non declare exception
     * @param e
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<ApiError> handleApiRequestException(Throwable e) {

        // Log stack message
        log.error(ThrowableUtils.getStackTrace(e));
        return buildResponseEntity(ApiError.error(e.getMessage()));
    }

    /**
     * BadCredentialsException
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> badCredentialsException(BadCredentialsException e){
        // 打印堆栈信息
        String message = String.format("Credential is not valid %s", e.getMessage());
        log.error(message);
        return buildResponseEntity(ApiError.error(message));
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException e) {

        String message = "Access Denied";

        log.error(String.format(message, e.getMessage()));

        return buildResponseEntity(ApiError.error(message), HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ApiError> handleBadCredentialException(AuthenticationException e) {

        String message = String.format("Credential is not valid %s", e.getMessage());

        log.error(message);

        return buildResponseEntity(ApiError.error(message), HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {

        log.error(ThrowableUtils.getStackTrace(ex));

        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((err) -> {
            String fieldName = ((FieldError)err).getField();
            String errMessage = err.getDefaultMessage();
            errors.put(fieldName, errMessage);
        });

        return buildResponseEntity(ApiError.error(String.format("Request body valid error $s", errors)), HttpStatus.BAD_REQUEST);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * Return
     */
    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
    }

    /**
     * Return Api Error
     * @param apiError
     * @param status
     * @return
     */
    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError, HttpStatus status) {
        return new ResponseEntity<>(apiError, status);
    }

}
