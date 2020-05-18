package com.jamesaq12wsx.gymtime.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jamesaq12wsx.gymtime.base.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 *
 */
@Getter
@Setter
public class UserDto extends BaseDto implements Serializable {

    private Long id;

    private String email;

    private String name;

    @JsonIgnore
    private String password;

    private String imageUrl;

    private String authProvider;

    private String providerId;

    private boolean emailVerify;

    private String role;

    @JsonIgnore
    private Map<String, Object> attributes;

    private UserInfoDto userInfo;

    private UserUnitSettingDto userUnitSetting;

}
