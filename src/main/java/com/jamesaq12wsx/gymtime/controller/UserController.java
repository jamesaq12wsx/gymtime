package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.model.ApiResponseBuilder;
import com.jamesaq12wsx.gymtime.model.entity.ApplicationUser;
import com.jamesaq12wsx.gymtime.auth.SelfUserDetailsService;
import com.jamesaq12wsx.gymtime.model.entity.UserInfo;
import com.jamesaq12wsx.gymtime.model.entity.UserUnitSetting;
import com.jamesaq12wsx.gymtime.model.payload.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
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

    @PostMapping("/update/unit")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateUnitSetting(Principal principal, UserUnitSetting setting){

        userDetailsService.updateUserUnitSetting(principal.getName(), setting);

        return apiResponseBuilder.createSuccessResponse(null);
    }

    @PostMapping("/update/info")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse updateUserInfo(Principal principal, UserInfo info){

        userDetailsService.updateUserInfo(principal.getName(), info);

        return apiResponseBuilder.createSuccessResponse(null);
    }

}
