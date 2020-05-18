package com.jamesaq12wsx.gymtime.model.entity;

import com.jamesaq12wsx.gymtime.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_height")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserHeight extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "measurement_unit", nullable = false)
    private MeasurementUnit measurementUnit;

    @Column(name = "height", nullable = false)
    private Double height;

}
