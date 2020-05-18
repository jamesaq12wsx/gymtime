package com.jamesaq12wsx.gymtime.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PostRecordDto implements Serializable {

    private Long id;

    private ExerciseDto exercise;

    private MeasurementUnitDto unit;

    private Double weight;

    private Double distance;

    private Integer duration;

    private Integer min;

    private Integer reps;

}
