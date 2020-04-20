package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jamesaq12wsx.gymtime.model.entity.ApplicationUser;
import com.jamesaq12wsx.gymtime.model.entity.Audit;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLHStoreType;
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
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "fitness_club")
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "hstore", typeClass = PostgreSQLHStoreType.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SimpleFitnessClub implements FitnessClub, Serializable, Auditable {

    @Id
    @Column(name = "club_uid")
    private UUID clubUuid;

    @Column(name = "club_name")
    private String clubName;

    @Column(name = "club_id")
    private int clubId;

    private double latitude;

    private double longitude;

    private String address;

    private String city;

    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "club_home_url")
    private String homeUrl;

    @Type(type = "hstore")
    @Column(name = "open_hours", columnDefinition = "hstore")
    private Map<String,String> openHours;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_brand")
    private SimpleBrand brand;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false, referencedColumnName = "email")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private ApplicationUser createdBy;

    private Audit audit;

}
