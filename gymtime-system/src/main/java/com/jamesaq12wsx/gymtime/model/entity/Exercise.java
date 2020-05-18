package com.jamesaq12wsx.gymtime.model.entity;

import com.jamesaq12wsx.gymtime.base.BaseEntity;
import com.jamesaq12wsx.gymtime.model.MeasurementType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
public class Exercise extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Exercise ID")
    private Integer id;

    @ApiModelProperty(value = "Exercise Name")
    private String name;

    @Column(name = "description", columnDefinition = "varchar default ''")
    @ApiModelProperty(value = "Exercise Description")
    private String description;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "Exercise Measurement Type")
    @Column(name = "measurement_type")
    private MeasurementType measurementType;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "muscle_group", nullable = false)
    @ApiModelProperty(value = "Exercise muscle group")
    private MuscleGroup muscleGroup;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "primary_muscle")
    @ApiModelProperty(value = "Exercise Primary Muscle")
    private Muscle primaryMuscle;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "secondary_muscle")
    @ApiModelProperty(value = "Exercise Secondary Muscle")
    private Muscle secondaryMuscle;

    @Type(type = "jsonb")
    @Column(name = "images", columnDefinition = "jsonb")
    @ApiModelProperty(value = "Exercise Images")
    private List<String> images;

}
