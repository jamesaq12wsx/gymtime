package com.jamesaq12wsx.gymtime.service.dto;

import com.jamesaq12wsx.gymtime.model.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class UserInfoDto implements Serializable {

    private String gender;

    private LocalDate birthday;

}
