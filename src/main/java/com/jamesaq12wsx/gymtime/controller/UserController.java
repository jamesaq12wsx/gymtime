package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.ApiResponseBuilder;
import com.jamesaq12wsx.gymtime.model.entity.User;
import com.jamesaq12wsx.gymtime.auth.SelfUserDetailsService;
import com.jamesaq12wsx.gymtime.model.entity.UserBodyRecord;
import com.jamesaq12wsx.gymtime.model.payload.*;
import com.jamesaq12wsx.gymtime.service.dto.UserBodyRecordDto;
import com.jamesaq12wsx.gymtime.service.dto.UserDto;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final ApiResponseBuilder apiResponseBuilder;

    private final SelfUserDetailsService userDetailsService;

    @Autowired
    public UserController(ApiResponseBuilder apiResponseBuilder, SelfUserDetailsService userDetailsService) {
        this.apiResponseBuilder = apiResponseBuilder;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse<UserDto> me(Principal principal){

        UserDto user = userDetailsService.loadUserInfoByEmail(principal.getName());

        ApiResponse<UserDto> apiResponse = new ApiResponse<>(
                true,
                "",
                user
        );

        return apiResponse;
    }

    @PutMapping("/name")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateUserName(@RequestBody UserInfoRequest request, Principal principal){

        if(request.getName() == null || StringUtil.isBlank(request.getName())){
            throw new ApiRequestException(String.format("Update name could not be empty"));
        }

        userDetailsService.updateUserName(request, principal);

        return apiResponseBuilder.createSuccessResponse(null);
    }

    @PutMapping("/picture")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse handleFileUpload(@RequestParam("picture") MultipartFile file, Principal principal) {

        if (file == null){
            throw new ApiRequestException(String.format("Cannot upload empty picture file"));
        }

        String mimeType = file.getContentType();

        String type = mimeType.split("/")[0];
        String subtype = mimeType.split("/")[1];

        if(!type.equals("image")){
            throw new ApiRequestException(String.format("User picture does not accept %s type", mimeType));
        }

        if (!subtype.equals("jpeg") && !subtype.equals("png")){
            throw new ApiRequestException(String.format("User picture does not accept %s type", mimeType));
        }

        userDetailsService.updateUserPicture(file, principal);

        return ApiResponseBuilder.createSuccessResponse(null);

    }

    @PutMapping("/gender")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateUserGender(@RequestBody UserInfoRequest request, Principal principal){

        if(request.getGender() == null){
            throw new ApiRequestException(String.format("Could not update user gender with empty value"));
        }

        userDetailsService.updateUserGender(request, principal);

        return apiResponseBuilder.createSuccessResponse(null);
    }

    @PutMapping("/birthday")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateUserBirthday(@RequestBody UserInfoRequest request, Principal principal){

        if(request.getBirthday() == null){
            throw new ApiRequestException(String.format("Could not update user birthday with empty value"));
        }

        userDetailsService.updateUserBirthday(request, principal);

        return apiResponseBuilder.createSuccessResponse(null);
    }

    @PostMapping("/unit/weight")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateWeightUnit(@RequestBody UserUnitRequest request, Principal principal){

        if(request.getWeightUnit() == null){
            throw new ApiRequestException(String.format("Could not update user weight unit, request is empty"));
        }

        userDetailsService.updateWeightUnit(request, principal);

        return apiResponseBuilder.createSuccessResponse(null);
    }

    @PostMapping("/unit/height")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateHeightUnit(@RequestBody UserUnitRequest request, Principal principal){

        if(request.getHeightUnit() == null){
            throw new ApiRequestException(String.format("Could not update user height unit, request is empty"));
        }

        userDetailsService.updateHeightUnit(request, principal);

        return apiResponseBuilder.createSuccessResponse(null);
    }

    @PostMapping("/unit/distance")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateDistanceUnit(@RequestBody UserUnitRequest request, Principal principal){

        if(request.getDistanceUnit() == null){
            throw new ApiRequestException(String.format("Could not update user height unit, request is empty"));
        }

        userDetailsService.updateDistanceUnit(request, principal);

        return apiResponseBuilder.createSuccessResponse(null);
    }

    @PostMapping("/info/gender")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateGender(@RequestBody UserInfoRequest request, Principal principal){

        if(request.getGender() == null){
            throw new ApiRequestException(String.format("Could not update user gender, request is empty"));
        }

        userDetailsService.updateGender(request, principal);

        return apiResponseBuilder.createSuccessResponse(null);
    }

    @PostMapping("/info/birthday")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateBirthday(@RequestBody UserInfoRequest request, Principal principal){

        if(request.getBirthday() == null){
            throw new ApiRequestException(String.format("Could not update user gender, request is empty"));
        }

        userDetailsService.updateBirthday(request, principal);

        return apiResponseBuilder.createSuccessResponse(null);
    }

    @GetMapping("/stat")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse<UserBodyRecordDto> getUserBodyStat(Principal principal){
        return ApiResponseBuilder.createSuccessResponse(userDetailsService.getUserBodyStat(principal));
    }

    @PostMapping("/height")
    public ApiResponse updateUserHeight(@RequestBody NewHeightRequest request, Principal principal){

        userDetailsService.updateUserHeight(request, principal);

        return ApiResponseBuilder.createSuccessResponse(true);

    }

    @PostMapping("/weight")
    public ApiResponse newUserWeight(@RequestBody NewWeightRequest request, Principal principal){

        userDetailsService.newUserWeight(request, principal);

        return ApiResponseBuilder.createSuccessResponse(true);

    }

    @DeleteMapping("/weight/{id}")
    public ApiResponse deleteUserWeight(@PathVariable("id") Integer id, Principal principal){

        userDetailsService.deleteUserWeight(id, principal);

        return ApiResponseBuilder.createSuccessResponse(true);

    }

    @PostMapping("/bodyfat")
    public ApiResponse newUserBodyFat(@RequestBody NewBodyFatRequest request, Principal principal){

        userDetailsService.newUserBodyFat(request, principal);

        return ApiResponseBuilder.createSuccessResponse(true);

    }

    @DeleteMapping("/bodyfat/{id}")
    public ApiResponse deleteUserBodyFat(@PathVariable("id") Integer id, Principal principal){

        userDetailsService.deleteUserBodyFat(id, principal);

        return ApiResponseBuilder.createSuccessResponse(true);

    }

}
