package com.jamesaq12wsx.gymtime.auth;

import com.jamesaq12wsx.gymtime.security.ApplicationUserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public class ApplicationUser implements UserDetails {

    //    final private Set<? extends  GrantedAuthority> grantedAuthorities;
    final private UUID uuid;
    final private String username;
    final private String password;
    final private String email;
    final private ApplicationUserRole role;
    final private boolean isAccountNonExpired;
    final private boolean isAccountNonLocked;
    final private boolean isCredentialsNonExpired;
    final private boolean isEnabled;
    final private LocalDateTime createdAt;
    final private LocalDateTime updatedAt;


    public ApplicationUser(
            UUID uuid,
            String username,
            String password,
//                           Set<? extends GrantedAuthority> grantedAuthorities,
            String email,
            ApplicationUserRole role,
            boolean isAccountNonExpired,
            boolean isAccountNonLocked,
            boolean isCredentialsNonExpired,
            boolean isEnabled,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
//        this.grantedAuthorities = grantedAuthorities;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.uuid = uuid;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getGrantedAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }

    public ApplicationUserRole getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
