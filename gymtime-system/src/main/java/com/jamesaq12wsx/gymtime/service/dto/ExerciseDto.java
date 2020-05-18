package com.jamesaq12wsx.gymtime.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ExerciseDto implements Serializable {

    private Integer id;

    private String name;

    private String description;

    private String measurementType;

    private MuscleGroupDto muscleGroup;

    private MuscleDto primaryMuscle;

    private MuscleDto secondaryMuscle;

    private List<String> images;

}
