package com.jamesaq12wsx.gymtime.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jamesaq12wsx.gymtime.auth.AuthProvider;
import com.jamesaq12wsx.gymtime.model.entity.UserInfo;
import com.jamesaq12wsx.gymtime.model.entity.UserUnitSetting;
import com.jamesaq12wsx.gymtime.security.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

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
