package com.jamesaq12wsx.gymtime.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jamesaq12wsx.gymtime.auth.AuthProvider;
import com.jamesaq12wsx.gymtime.security.Role;
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
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "gym_time_user")
@TypeDef(name = "hstore", typeClass = PostgreSQLHStoreType.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "user_uuid")
//    private UUID uuid;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;

    @Email
    @Column(name = "username", nullable = false, updatable = false)
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
    private Role role;

    @JsonIgnore
    @Type(type = "hstore")
    @Column(name = "attributes", columnDefinition = "hstore")
    private Map<String, Object> attributes;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "post_privacy")
    @Type( type = "pgsql_enum" )
    private PostPrivacy privacy;

    @Embedded
    private UserInfo userInfo;

    @Embedded
    private UserUnitSetting userUnitSetting;

}
