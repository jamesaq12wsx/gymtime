package com.jamesaq12wsx.gymtime.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;


public enum Role {
    ANONYMOUS(Sets.newHashSet(ApplicationUserPermission.CLUB_READ, ApplicationUserPermission.INFO_READ, ApplicationUserPermission.EXERCISE_READ)),
    USER(Sets.newHashSet(ApplicationUserPermission.USER_READ, ApplicationUserPermission.USER_WRITE, ApplicationUserPermission.CLUB_READ, ApplicationUserPermission.INFO_READ, ApplicationUserPermission.POST_READ, ApplicationUserPermission.POST_WRITE, ApplicationUserPermission.EXERCISE_READ, ApplicationUserPermission.EXERCISE_WRITE)),
    ADMIN(Sets.newHashSet(ApplicationUserPermission.USER_READ, ApplicationUserPermission.USER_WRITE, ApplicationUserPermission.CLUB_READ, ApplicationUserPermission.CLUB_WRITE, ApplicationUserPermission.POST_READ, ApplicationUserPermission.POST_WRITE, ApplicationUserPermission.INFO_READ, ApplicationUserPermission.INFO_WRITE, ApplicationUserPermission.EXERCISE_READ, ApplicationUserPermission.EXERCISE_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(ApplicationUserPermission.USER_READ, ApplicationUserPermission.CLUB_READ, ApplicationUserPermission.POST_READ, ApplicationUserPermission.INFO_READ, ApplicationUserPermission.EXERCISE_READ));

    private final Set<ApplicationUserPermission> permissions;

    Role(Set<ApplicationUserPermission> permissions) {
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
