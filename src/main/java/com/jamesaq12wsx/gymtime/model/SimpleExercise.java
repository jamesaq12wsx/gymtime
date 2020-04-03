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
import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "exercise_category_exercise",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<SimpleExCategory> category;

    @Type(type = "jsonb")
    @Column(name = "images", columnDefinition = "jsonb")
    private List<String> images;

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
