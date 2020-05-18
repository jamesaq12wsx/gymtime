package com.jamesaq12wsx.gymtime.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class UserBodyFatDto implements Serializable {

    private Long id;

    private Double bodyFat;

    private LocalDate date;

}
