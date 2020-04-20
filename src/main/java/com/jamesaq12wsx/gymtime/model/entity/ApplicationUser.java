package com.jamesaq12wsx.gymtime.model.entity;

import com.jamesaq12wsx.gymtime.auth.AuthProvider;
import com.jamesaq12wsx.gymtime.model.Auditable;
import com.jamesaq12wsx.gymtime.security.ApplicationUserRole;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLHStoreType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "gym_time_user")
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

    @Type(type = "hstore")
    @Column(name = "attributes", columnDefinition = "hstore")
    private Map<String, Object> attributes;

    @Embedded
    private UserInfo userInfo;

    @Embedded
    private UserUnitSetting userUnitSetting;

    @Embedded
    private Audit audit;

//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;

//    @PrePersist
//    public void beforeInsert(){
//        LocalDateTime now = LocalDateTime.now();
//        this.uuid = UUID.randomUUID();
//        this.createdAt = now;
//        this.updatedAt = now;
//    }
//
//    @PreUpdate
//    public void beforeUpdate(){
//        LocalDateTime now = LocalDateTime.now();
//        this.updatedAt = now;
//    }

}
