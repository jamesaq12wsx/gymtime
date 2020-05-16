package com.jamesaq12wsx.gymtime.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "post_record")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostRecord extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise", nullable = false)
    private Exercise exercise;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measurement_unit", nullable = false)
    private MeasurementUnit measurementUnit;

    private Double weight;

    private Double distance;

    private Integer duration;

    private Integer min;

    private Integer reps;

}
