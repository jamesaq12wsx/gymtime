package com.jamesaq12wsx.gymtime.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class MuscleGroupDto implements Serializable {

    private Integer id;

    private String name;

}
