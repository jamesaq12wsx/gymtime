package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "muscle_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleMuscleGroup implements MuscleGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank
    @Column(unique = true)
    private String name;

}
