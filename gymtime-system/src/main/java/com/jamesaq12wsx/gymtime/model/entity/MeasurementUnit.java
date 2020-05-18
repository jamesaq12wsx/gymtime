package com.jamesaq12wsx.gymtime.model.entity;

import com.jamesaq12wsx.gymtime.base.BaseEntity;
import com.jamesaq12wsx.gymtime.model.MeasurementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "measurement_unit")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeasurementUnit extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "measurement_type")
    private MeasurementType measurementType;

    private String name;

    private String alias;

}
