package com.jamesaq12wsx.gymtime.security;

public enum ApplicationUserPermission {

    USER_READ("user:read"),
    USER_WRITE("user:write"),
    INFO_READ("info:read"),
    INFO_WRITE("info:write"),
    POST_READ("post:read"),
    POST_WRITE("post:write"),
    CLUB_READ("club:read"),
    CLUB_WRITE("club:write"),
    EXERCISE_READ("exercise:read"),
    EXERCISE_WRITE("exercise:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
