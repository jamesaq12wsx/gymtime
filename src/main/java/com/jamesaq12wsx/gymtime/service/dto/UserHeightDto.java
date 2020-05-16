package com.jamesaq12wsx.gymtime.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jamesaq12wsx.gymtime.model.entity.MeasurementUnit;
import com.jamesaq12wsx.gymtime.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
public class UserHeightDto implements Serializable {

    private Long id;

    @OneToOne
    @JoinColumn(name = "measurement_unit", nullable = false)
    private MeasurementUnit measurementUnit;

    @Column(name = "height", nullable = false)
    private Double height;

}
