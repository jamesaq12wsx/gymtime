package com.jamesaq12wsx.gymtime.model;

import com.jamesaq12wsx.gymtime.exception.ApiException;
import com.jamesaq12wsx.gymtime.model.payload.ApiResponse;
import org.springframework.stereotype.Component;

@Component
public class ApiResponseBuilder {

    public static ApiResponse createSuccessResponse(Object result){
        return new ApiResponse(true, "", result);
    }

    public static ApiResponse createFailedResponse(String message){
        return new ApiResponse(false, message, null);
    }

    public static ApiResponse createFailedResponse(String message, Object result){
        return new ApiResponse(false, message, result);
    }

}
