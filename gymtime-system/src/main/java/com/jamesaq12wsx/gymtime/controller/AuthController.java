package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.auth.SelfUserDetailsService;
import com.jamesaq12wsx.gymtime.jwt.TokenProvider;
import com.jamesaq12wsx.gymtime.model.payload.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final SelfUserDetailsService userDetailsService;

    private final TokenProvider tokenProvider;

    @Autowired
    public AuthController(SelfUserDetailsService userDetailsService, TokenProvider tokenProvider) {
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signup")
    public void signUp(@Valid @RequestBody SignUpRequest request){

        userDetailsService.addNewUser(request);
    }

    @GetMapping("/check")
    public void verifyToken(){
        return;
    }

}
