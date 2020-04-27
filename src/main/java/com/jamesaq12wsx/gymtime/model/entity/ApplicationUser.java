package com.jamesaq12wsx.gymtime.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jamesaq12wsx.gymtime.auth.AuthProvider;
import com.jamesaq12wsx.gymtime.model.Auditable;
import com.jamesaq12wsx.gymtime.model.MeasurementType;
import com.jamesaq12wsx.gymtime.model.SimpleMeasurementUnit;
import com.jamesaq12wsx.gymtime.security.ApplicationUserRole;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLHStoreType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "gym_time_user")
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "hstore", typeClass = PostgreSQLHStoreType.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplicationUser implements Auditable, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_uuid")
    private UUID uuid;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(name = "name")
    private String name;

    private String password;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    private AuthProvider authProvider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "email_verify")
    private boolean emailVerify;

    @Enumerated(EnumType.STRING)
    private ApplicationUserRole role;

    @JsonIgnore
    @Type(type = "hstore")
    @Column(name = "attributes", columnDefinition = "hstore")
    private Map<String, Object> attributes;

    @Embedded
    private UserInfo userInfo;

    @Embedded
    private UserUnitSetting userUnitSetting;

    @JsonIgnore
    @Embedded
    private Audit audit;

//    @PrePersist
//    public void beforeInsert(){
//        OffsetDateTime now = OffsetDateTime.now();
//        this.uuid = UUID.randomUUID();
//        if(this.audit == null){
//            this.audit = new Audit();
//        }
//
//        UserUnitSetting defaultSetting = new UserUnitSetting();
//        defaultSetting.setHeightUnit(new SimpleMeasurementUnit(1, MeasurementType.HEIGHT, "Centimeter", "cm"));
//        defaultSetting.setWeightUnit(new SimpleMeasurementUnit(4, MeasurementType.WEIGHT, "Kilogram", "kg"));
//        defaultSetting.setDistanceUnit(new SimpleMeasurementUnit(6, MeasurementType.DISTANCE, "kilometre", "km"));
//
//        this.setUserUnitSetting(defaultSetting);
//
//    }

//    @PreUpdate
//    public void beforeUpdate(){
//        LocalDateTime now = LocalDateTime.now();
//        this.updatedAt = now;
//    }

}
