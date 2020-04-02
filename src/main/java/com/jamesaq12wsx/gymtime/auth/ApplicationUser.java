package com.jamesaq12wsx.gymtime.auth;

import com.jamesaq12wsx.gymtime.security.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "gym_time_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplicationUser implements UserDetails {

    //    final private Set<? extends  GrantedAuthority> grantedAuthorities;
    @Id
    @Column(name = "user_uuid")
    private UUID uuid;

    private String username;

    private String password;

    @Column(name = "facebook_id")
    private String facebookId;

    private String email;

    @Enumerated(EnumType.STRING)
    private ApplicationUserRole role;

    @Transient
    private boolean isAccountNonExpired;

    @Transient
    private boolean isAccountNonLocked;

    @Transient
    private boolean isCredentialsNonExpired;

    @Column(name = "is_enable")
    private boolean isEnabled;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getGrantedAuthorities();
    }

//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
//
    @Override
    public boolean isEnabled() {
        return true;
    }
//
//    public UUID getUuid() {
//        return uuid;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public ApplicationUserRole getRole() {
//        return role;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
}
