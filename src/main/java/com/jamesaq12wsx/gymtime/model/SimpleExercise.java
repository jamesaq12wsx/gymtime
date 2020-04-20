package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jamesaq12wsx.gymtime.model.entity.ApplicationUser;
import com.jamesaq12wsx.gymtime.model.entity.Audit;
import com.jamesaq12wsx.gymtime.model.payload.SimpleMeasurementType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "exercise")
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SimpleExercise implements Exercise, Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "description", columnDefinition = "varchar default ''")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "measurement_type")
    private MeasurementType measurementType;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "muscle_group", nullable = false)
    private SimpleMuscleGroup muscleGroup;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "primary_muscle")
    private SimpleMuscle primaryMuscle;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "secondary_muscle")
    private SimpleMuscle secondaryMuscle;

    @Type(type = "jsonb")
    @Column(name = "images", columnDefinition = "jsonb")
    private List<String> images;

    @Column(name = "system", nullable = false, columnDefinition = "boolean default false")
    private Boolean system;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", referencedColumnName = "email", updatable = false, nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private ApplicationUser createdBy;

    @Embedded
    @JsonIgnore
    private Audit audit;
}
