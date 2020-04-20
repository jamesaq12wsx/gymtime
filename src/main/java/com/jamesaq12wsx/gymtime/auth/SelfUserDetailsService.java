package com.jamesaq12wsx.gymtime.auth;

import com.jamesaq12wsx.gymtime.model.entity.ApplicationUser;
import com.jamesaq12wsx.gymtime.model.entity.UserInfo;
import com.jamesaq12wsx.gymtime.model.entity.UserUnitSetting;
import com.jamesaq12wsx.gymtime.model.payload.SignUpRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;

public interface SelfUserDetailsService extends UserDetailsService {

    void addNewUser(SignUpRequest request);

    ApplicationUser loadUserInfoByEmail(String email);

    ApplicationUser updateUserUnitSetting(String email, UserUnitSetting setting);

    ApplicationUser updateUserInfo(String email, UserInfo setting);

}
