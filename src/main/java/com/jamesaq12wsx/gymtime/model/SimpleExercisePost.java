package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "exercise_post")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SimpleExercisePost implements ExercisePost, Auditable {

    @Id
    @Column(name = "post_uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID postUuid;

    @Column(name = "post_time")
    private LocalDateTime exerciseTime;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "post_privacy")
    @Type( type = "pgsql_enum" )
    private PostPrivacy privacy;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location")
    private SimpleFitnessClub club;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "exercise_post_exercise", joinColumns = @JoinColumn(name = "post_uuid"))
    private List<SimplePostExercise> exercises;

    @Embedded
    private Audit audit;

    @Override
    public UUID getClubUuid() {
        if (club == null){
            return null;
        }
        return club.getClubUuid();
    }

    @PrePersist
    protected void onCreate() {

        if (audit == null){
            audit = new Audit();
        }

        LocalDateTime now = LocalDateTime.now();
        audit.setCreatedAt(now);
        audit.setUpdatedAt(now);
    }

    @PreUpdate
    protected void onUpdate() {

        if (audit == null){
            audit = new Audit();
        }

        LocalDateTime now = LocalDateTime.now();
        audit.setUpdatedAt(now);

    }

}
