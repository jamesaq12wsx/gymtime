package com.jamesaq12wsx.gymtime.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserUnitSettingDto implements Serializable {

    private MeasurementUnitDto heightUnit;

    private MeasurementUnitDto weightUnit;

    private MeasurementUnitDto distanceUnit;

}
