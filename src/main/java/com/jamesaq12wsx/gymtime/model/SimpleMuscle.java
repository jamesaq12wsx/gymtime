package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "muscle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleMuscle implements Muscle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

}
