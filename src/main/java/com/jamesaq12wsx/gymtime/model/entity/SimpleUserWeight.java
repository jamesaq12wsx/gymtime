package com.jamesaq12wsx.gymtime.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jamesaq12wsx.gymtime.model.SimpleMeasurementUnit;
import com.jamesaq12wsx.gymtime.model.entity.ApplicationUser;
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
public class SimpleUserWeight implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "username", referencedColumnName = "email", nullable = false, updatable = false)
    @JsonIgnore
    private ApplicationUser user;

    @OneToOne
    @JoinColumn(name = "measurement_unit", nullable = false)
    private SimpleMeasurementUnit measurementUnit;

    private Double weight;

    private LocalDate date;

}
