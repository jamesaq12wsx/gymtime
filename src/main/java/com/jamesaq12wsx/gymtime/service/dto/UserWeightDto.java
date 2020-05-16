package com.jamesaq12wsx.gymtime.service.dto;

import com.jamesaq12wsx.gymtime.model.entity.MeasurementUnit;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class UserWeightDto implements Serializable {

    private Long id;

    private MeasurementUnitDto measurementUnit;

    private Double weight;

    private LocalDate date;

}
