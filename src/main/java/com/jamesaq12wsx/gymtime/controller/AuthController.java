package com.jamesaq12wsx.gymtime.controller;

import com.jamesaq12wsx.gymtime.auth.SelfUserDetailsService;
import com.jamesaq12wsx.gymtime.model.payload.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final SelfUserDetailsService userDetailsService;

    @Autowired
    public AuthController(SelfUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody SignUpRequest request){

        userDetailsService.addNewUser(request);
    }

    @GetMapping("/check")
    public void verifyToken(){
        return;
    }

}
