package com.jamesaq12wsx.gymtime.auth;

import com.jamesaq12wsx.gymtime.model.entity.ApplicationUser;
import com.jamesaq12wsx.gymtime.model.entity.UserBodyStat;
import com.jamesaq12wsx.gymtime.model.entity.UserInfo;
import com.jamesaq12wsx.gymtime.model.entity.UserUnitSetting;
import com.jamesaq12wsx.gymtime.model.payload.NewBodyFatRequest;
import com.jamesaq12wsx.gymtime.model.payload.NewHeightRequest;
import com.jamesaq12wsx.gymtime.model.payload.NewWeightRequest;
import com.jamesaq12wsx.gymtime.model.payload.SignUpRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;

public interface SelfUserDetailsService extends UserDetailsService {

    void addNewUser(SignUpRequest request);

    ApplicationUser loadUserInfoByEmail(String email);

    ApplicationUser updateUserUnitSetting(String email, UserUnitSetting setting);

    ApplicationUser updateUserInfo(String email, UserInfo setting);

    UserBodyStat getUserBodyStat(Principal principal);

    void updateUserHeight(NewHeightRequest request, Principal principal);

    void newUserWeight(NewWeightRequest request, Principal principal);

    void deleteUserWeight(Integer id, Principal principal);

    void newUserBodyFat(NewBodyFatRequest request, Principal principal);

    void deleteUserBodyFat(Integer id, Principal principal);

}
