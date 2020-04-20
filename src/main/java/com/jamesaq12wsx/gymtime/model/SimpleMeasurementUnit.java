package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "measurement_unit")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SimpleMeasurementUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "measurement_type")
    private MeasurementType measurementType;

    private String name;

    private String alias;

}
