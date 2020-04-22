package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.model.ApiResponseBuilder;
import com.jamesaq12wsx.gymtime.model.entity.ApplicationUser;
import com.jamesaq12wsx.gymtime.auth.SelfUserDetailsService;
import com.jamesaq12wsx.gymtime.model.entity.UserBodyStat;
import com.jamesaq12wsx.gymtime.model.entity.UserInfo;
import com.jamesaq12wsx.gymtime.model.entity.UserUnitSetting;
import com.jamesaq12wsx.gymtime.model.payload.ApiResponse;
import com.jamesaq12wsx.gymtime.model.payload.NewBodyFatRequest;
import com.jamesaq12wsx.gymtime.model.payload.NewHeightRequest;
import com.jamesaq12wsx.gymtime.model.payload.NewWeightRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<ApplicationUser> me(Principal principal){

        ApplicationUser user = userDetailsService.loadUserInfoByEmail(principal.getName());

        ApiResponse<ApplicationUser> apiResponse = new ApiResponse<>(
                true,
                "",
                user
        );

        return apiResponse;
    }

    @PostMapping("/unit")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateUnitSetting(Principal principal,@RequestBody UserUnitSetting setting){

        userDetailsService.updateUserUnitSetting(principal.getName(), setting);

        return apiResponseBuilder.createSuccessResponse(null);
    }

    @PostMapping("/info")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateUserInfo(Principal principal,@RequestBody UserInfo info){

        userDetailsService.updateUserInfo(principal.getName(), info);

        return apiResponseBuilder.createSuccessResponse(null);
    }

    @GetMapping("/stat")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse<UserBodyStat> getUserBodyStat(Principal principal){
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
