package com.jamesaq12wsx.gymtime.model.entity;

import com.jamesaq12wsx.gymtime.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "muscle_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MuscleGroup extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank
    @Column(unique = true)
    private String name;

}
