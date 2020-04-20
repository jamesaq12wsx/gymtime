package com.jamesaq12wsx.gymtime.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jamesaq12wsx.gymtime.model.SimpleExercise;
import com.jamesaq12wsx.gymtime.model.SimpleMeasurementUnit;
import com.jamesaq12wsx.gymtime.model.SimplePost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "post_record")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SimplePostRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_uuid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SimplePost post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise", nullable = false)
    private SimpleExercise exercise;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measurement_unit", nullable = false)
    private SimpleMeasurementUnit measurementUnit;

    private Double weight;

    private Double distance;

    private Integer duration;

    private Integer min;

    private Integer reps;

}
