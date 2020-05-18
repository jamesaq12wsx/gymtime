package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.annotation.Log;
import com.jamesaq12wsx.gymtime.service.dto.UserDto;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.ApiResponseBuilder;
import com.jamesaq12wsx.gymtime.auth.SelfUserDetailsService;
import com.jamesaq12wsx.gymtime.model.payload.*;
import com.jamesaq12wsx.gymtime.service.dto.UserBodyRecordDto;
import com.jamesaq12wsx.gymtime.util.SecurityUtils;
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

    @Log("User Get Self Data")
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse<UserDto> me(){

        UserDto user = userDetailsService.loadUserInfoByEmail(getCurrentUsername());

        ApiResponse<UserDto> apiResponse = new ApiResponse<>(
                true,
                "",
                user
        );

        return apiResponse;
    }

    @Log("User Update Name")
    @PutMapping("/name")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateUserName(@RequestBody UserInfoRequest request){

        if(request.getName() == null || StringUtil.isBlank(request.getName())){
            throw new ApiRequestException(String.format("Update name could not be empty"));
        }

        userDetailsService.updateUserName(request);

        return apiResponseBuilder.createSuccessResponse(null);
    }

    @Log("User Update Picture")
    @PutMapping("/picture")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse handleFileUpload(@RequestParam("picture") MultipartFile file) {

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

        userDetailsService.updateUserPicture(file);

        return ApiResponseBuilder.createSuccessResponse(null);

    }

    @Log("User Update Wieght Unit")
    @PostMapping("/unit/weight")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateWeightUnit(@RequestBody UserUnitRequest request){

        if(request.getWeightUnit() == null){
            throw new ApiRequestException(String.format("Could not update user weight unit, request is empty"));
        }

        userDetailsService.updateWeightUnit(request);

        return apiResponseBuilder.createSuccessResponse(null);
    }

    @Log("User Update Height Unit")
    @PostMapping("/unit/height")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateHeightUnit(@RequestBody UserUnitRequest request){

        if(request.getHeightUnit() == null){
            throw new ApiRequestException(String.format("Could not update user height unit, request is empty"));
        }

        userDetailsService.updateHeightUnit(request);

        return apiResponseBuilder.createSuccessResponse(null);
    }

    @Log("User Update Distance Unit")
    @PostMapping("/unit/distance")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateDistanceUnit(@RequestBody UserUnitRequest request){

        if(request.getDistanceUnit() == null){
            throw new ApiRequestException(String.format("Could not update user height unit, request is empty"));
        }

        userDetailsService.updateDistanceUnit(request);

        return apiResponseBuilder.createSuccessResponse(null);
    }

    @Log("User Update Gender")
    @PostMapping("/info/gender")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateGender(@RequestBody UserInfoRequest request){

        if(request.getGender() == null){
            throw new ApiRequestException(String.format("Could not update user gender, request is empty"));
        }

        userDetailsService.updateGender(request);

        return apiResponseBuilder.createSuccessResponse(null);
    }

    @Log("User Update Birthday")
    @PostMapping("/info/birthday")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateBirthday(@RequestBody UserInfoRequest request){

        if(request.getBirthday() == null){
            throw new ApiRequestException(String.format("Could not update user gender, request is empty"));
        }

        userDetailsService.updateBirthday(request);

        return apiResponseBuilder.createSuccessResponse(null);
    }

    @Log("User Get Self Body Stat Data")
    @GetMapping("/stat")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse<UserBodyRecordDto> getUserBodyStat(){
        return ApiResponseBuilder.createSuccessResponse(userDetailsService.getUserBodyStat());
    }

    @Log("User Update Height")
    @PostMapping("/height")
    public ApiResponse updateUserHeight(@RequestBody NewHeightRequest request){

        userDetailsService.updateUserHeight(request);

        return ApiResponseBuilder.createSuccessResponse(true);

    }

    @Log("User Add Weight Record")
    @PostMapping("/weight")
    public ApiResponse newUserWeight(@RequestBody NewWeightRequest request){

        userDetailsService.newUserWeight(request);

        return ApiResponseBuilder.createSuccessResponse(true);

    }

    @Log("User Delete Weight Record")
    @DeleteMapping("/weight/{id}")
    public ApiResponse deleteUserWeight(@PathVariable("id") Integer id){

        userDetailsService.deleteUserWeight(id);

        return ApiResponseBuilder.createSuccessResponse(true);

    }

    @Log("User Add BodyFat Record")
    @PostMapping("/bodyfat")
    public ApiResponse newUserBodyFat(@RequestBody NewBodyFatRequest request){

        userDetailsService.newUserBodyFat(request);

        return ApiResponseBuilder.createSuccessResponse(true);

    }

    @Log("User Delete Body Fat Record")
    @DeleteMapping("/bodyfat/{id}")
    public ApiResponse deleteUserBodyFat(@PathVariable("id") Integer id){

        userDetailsService.deleteUserBodyFat(id);

        return ApiResponseBuilder.createSuccessResponse(true);

    }

    private Principal getCurrentUser(){
        return SecurityUtils.getCurrentAuthentication();
    }

    private String getCurrentUsername(){
        return SecurityUtils.getCurrentUsername();
    }

}
