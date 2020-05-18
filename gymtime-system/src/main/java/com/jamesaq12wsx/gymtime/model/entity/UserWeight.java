package com.jamesaq12wsx.gymtime.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "user_weight")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserWeight extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "username", insertable = false, updatable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "measurement_unit", nullable = false)
    private MeasurementUnit measurementUnit;

    private Double weight;

    private LocalDate date;

}
