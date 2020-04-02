package com.jamesaq12wsx.gymtime.model;

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
    private UUID postUuid;

    @Column(name = "post_time")
    private LocalDateTime exerciseTime;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "post_privacy")
    @Type( type = "pgsql_enum" )
    private PostPrivacy privacy;

    @Column(name = "location")
    private UUID clubUuid;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "exercise_post_exercise", joinColumns = @JoinColumn(name = "post_uuid"))
    private List<SimplePostExercise> exercises;

    @Embedded
    private Audit audit;

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
