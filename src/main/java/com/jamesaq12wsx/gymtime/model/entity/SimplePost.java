package com.jamesaq12wsx.gymtime.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jamesaq12wsx.gymtime.model.Auditable;
import com.jamesaq12wsx.gymtime.model.Post;
import com.jamesaq12wsx.gymtime.model.PostPrivacy;
import com.jamesaq12wsx.gymtime.model.SimpleFitnessClub;
import com.jamesaq12wsx.gymtime.model.entity.ApplicationUser;
import com.jamesaq12wsx.gymtime.model.entity.Audit;
import com.jamesaq12wsx.gymtime.model.entity.SimplePostRecord;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
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
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "post")
@EntityListeners(AuditingEntityListener.class)
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SimplePost implements Post, Auditable {

    @Id
    @Column(name = "post_uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID postUuid;

    @Column(name = "time", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime exerciseTime;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "post_privacy")
    @Type( type = "pgsql_enum" )
    private PostPrivacy privacy;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location")
    private SimpleFitnessClub club;

    @OneToMany
    @JoinColumn(name = "post_uuid")
    @JsonIgnoreProperties("post")
    private List<SimplePostRecord> records;

//    @ElementCollection(fetch = FetchType.LAZY)
//    @CollectionTable(name = "exercise_post_exercise", joinColumns = @JoinColumn(name = "post_uuid"))
//    private List<SimplePostExercise> exercises;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false, referencedColumnName = "email")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private ApplicationUser user;

    @Embedded
    private Audit audit;

//    @Override
//    public UUID getClubUuid() {
//        if (club == null){
//            return null;
//        }
//        return club.getClubUuid();
//    }

//    @PrePersist
//    protected void onCreate() {
//
//        if (audit == null){
//            audit = new Audit();
//        }
//
//        LocalDateTime now = LocalDateTime.now();
//        audit.setCreatedAt(now);
//        audit.setUpdatedAt(now);
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//
//        if (audit == null){
//            audit = new Audit();
//        }
//
//        LocalDateTime now = LocalDateTime.now();
//        audit.setUpdatedAt(now);
//
//    }

}
