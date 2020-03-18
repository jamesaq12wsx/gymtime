package com.jamesaq12wsx.gymtime.auth;

import com.jamesaq12wsx.gymtime.jwt.SignUpRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SelfUserDetailsService extends UserDetailsService {

    public void addNewUser(SignUpRequest request);

}
