package com.jamesaq12wsx.gymtime.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "muscle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Muscle extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

}
