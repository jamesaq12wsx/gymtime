package com.jamesaq12wsx.gymtime.security;

public enum ApplicationUserPermission {

    USER_READ("user:read"),
    USER_WRITE("user:write"),
    MARK_READ("mark:read"),
    MARK_WRITE("mark:write"),
    CLUB_READ("club:read"),
    CLUB_WRITE("club:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
