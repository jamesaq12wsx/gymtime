package com.jamesaq12wsx.gymtime.model.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jamesaq12wsx.gymtime.model.MeasurementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExerciseRequest {

    @NotBlank
    private String name;

    private String description = "";

    private final MeasurementType measurementType = MeasurementType.WEIGHT;

    @NotNull
    @JsonProperty("muscleGroup")
    private Integer muscleGroupId;

    @JsonProperty("primaryMuscle")
    private Integer primaryMuscleId;

    @JsonProperty("secondaryMuscle")
    private Integer secondaryMuscleId;

    private MultipartFile[] images;

}
