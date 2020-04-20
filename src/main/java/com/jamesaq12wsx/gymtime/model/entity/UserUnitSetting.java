package com.jamesaq12wsx.gymtime.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class UserUnitSetting {

    private int heightUnit;

    private int weightUnit;

    private int distanceUnit;

}
