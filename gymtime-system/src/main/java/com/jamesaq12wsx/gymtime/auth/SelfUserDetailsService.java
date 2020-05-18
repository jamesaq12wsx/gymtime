package com.jamesaq12wsx.gymtime.auth;

import com.jamesaq12wsx.gymtime.service.dto.UserBodyRecordDto;
import com.jamesaq12wsx.gymtime.service.dto.UserDto;
import com.jamesaq12wsx.gymtime.model.payload.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 *
 */
public interface SelfUserDetailsService extends UserDetailsService {

    void addNewUser(SignUpRequest request);

    UserDto loadUserInfoByEmail(String email);

    void updateUserName(UserInfoRequest request);

    void updateUserBirthday(UserInfoRequest request);

    void updateUserGender(UserInfoRequest request);

    void updateUserPicture(MultipartFile picture);

    void updateWeightUnit(UserUnitRequest request);

    void updateHeightUnit(UserUnitRequest request);

    void updateDistanceUnit(UserUnitRequest request);

    void updateGender(UserInfoRequest request);

    void updateBirthday(UserInfoRequest request);

    UserBodyRecordDto getUserBodyStat();

    void updateUserHeight(NewHeightRequest request);

    void newUserWeight(NewWeightRequest request);

    void deleteUserWeight(Integer id);

    void newUserBodyFat(NewBodyFatRequest request);

    void deleteUserBodyFat(Integer id);

}
