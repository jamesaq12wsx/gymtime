package com.jamesaq12wsx.gymtime.model;

import com.jamesaq12wsx.gymtime.auth.ApplicationUser;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLHStoreType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "exercise")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SimpleExercise implements Exercise, Auditable {

    @Id
    private int id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "exercise_category")
    @Type( type = "pgsql_enum" )
    private ExerciseCategory category;

    @Type(type = "jsonb")
    @Column(name = "images", columnDefinition = "jsonb")
    private List<String> images;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "created_by", referencedColumnName = "username")
//    @Column(name = "created_by")
//    private String createdBy;
//
//    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
//    private LocalDateTime updatedAt;

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
