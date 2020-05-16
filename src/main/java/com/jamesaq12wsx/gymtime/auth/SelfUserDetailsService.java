package com.jamesaq12wsx.gymtime.auth;

import com.jamesaq12wsx.gymtime.model.entity.User;
import com.jamesaq12wsx.gymtime.model.entity.UserBodyRecord;
import com.jamesaq12wsx.gymtime.model.payload.*;
import com.jamesaq12wsx.gymtime.service.dto.UserBodyRecordDto;
import com.jamesaq12wsx.gymtime.service.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 *
 */
public interface SelfUserDetailsService extends UserDetailsService {

    void addNewUser(SignUpRequest request);

    UserDto loadUserInfoByEmail(String email);

    void updateUserName(UserInfoRequest request, Principal principal);

    void updateUserBirthday(UserInfoRequest request, Principal principal);

    void updateUserGender(UserInfoRequest request, Principal principal);

    void updateUserPicture(MultipartFile picture, Principal principal);

    void updateWeightUnit(UserUnitRequest request, Principal principal);

    void updateHeightUnit(UserUnitRequest request, Principal principal);

    void updateDistanceUnit(UserUnitRequest request, Principal principal);

    void updateGender(UserInfoRequest request, Principal principal);

    void updateBirthday(UserInfoRequest request, Principal principal);

    UserBodyRecordDto getUserBodyStat(Principal principal);

    void updateUserHeight(NewHeightRequest request, Principal principal);

    void newUserWeight(NewWeightRequest request, Principal principal);

    void deleteUserWeight(Integer id, Principal principal);

    void newUserBodyFat(NewBodyFatRequest request, Principal principal);

    void deleteUserBodyFat(Integer id, Principal principal);

}
