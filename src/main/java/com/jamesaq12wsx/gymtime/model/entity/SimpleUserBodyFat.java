package com.jamesaq12wsx.gymtime.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jamesaq12wsx.gymtime.model.entity.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "user_body_fat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SimpleUserBodyFat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "username", referencedColumnName = "email", nullable = false, updatable = false)
    @JsonIgnore
    private ApplicationUser user;

    @Column(name = "body_fat", nullable = false)
    private Double bodyFat;

    @Column(nullable = false)
    private LocalDate date;

}
