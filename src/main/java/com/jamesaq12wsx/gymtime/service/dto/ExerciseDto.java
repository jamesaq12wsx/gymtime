package com.jamesaq12wsx.gymtime.service.dto;

import com.jamesaq12wsx.gymtime.model.MeasurementType;
import com.jamesaq12wsx.gymtime.model.entity.Muscle;
import com.jamesaq12wsx.gymtime.model.entity.MuscleGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
