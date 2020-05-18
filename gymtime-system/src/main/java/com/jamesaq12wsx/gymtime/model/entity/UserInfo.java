package com.jamesaq12wsx.gymtime.model.entity;

import com.jamesaq12wsx.gymtime.model.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Embeddable
@Getter
@Setter
public class UserInfo {

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthday;

}
