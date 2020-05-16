package com.jamesaq12wsx.gymtime.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jamesaq12wsx.gymtime.model.entity.Exercise;
import com.jamesaq12wsx.gymtime.model.entity.MeasurementUnit;
import com.jamesaq12wsx.gymtime.model.entity.Post;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

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
