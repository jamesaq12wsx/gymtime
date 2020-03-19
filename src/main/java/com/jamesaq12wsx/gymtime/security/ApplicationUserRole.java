package com.jamesaq12wsx.gymtime.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.jamesaq12wsx.gymtime.security.ApplicationUserPermission.*;


public enum ApplicationUserRole {
    USER(Sets.newHashSet(USER_READ, USER_WRITE, MARK_READ, MARK_WRITE)),
    ADMIN(Sets.newHashSet(USER_READ, USER_WRITE, CLUB_READ, CLUB_WRITE,MARK_READ, MARK_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(USER_READ, CLUB_READ,MARK_READ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + name()));

        return permissions;
    }
}