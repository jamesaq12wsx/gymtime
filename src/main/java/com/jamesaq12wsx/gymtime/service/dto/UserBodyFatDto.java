package com.jamesaq12wsx.gymtime.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jamesaq12wsx.gymtime.model.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class UserBodyFatDto implements Serializable {

    private Long id;

    private Double bodyFat;

    private LocalDate date;

}
