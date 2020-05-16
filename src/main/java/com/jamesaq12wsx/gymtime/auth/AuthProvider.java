package com.jamesaq12wsx.gymtime.auth;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AuthProvider {

    LOCAL("local", "Email sign up"),
    FACEBOOK("facebook", "Facebook oauth sign up"),
    GOOGLE("google", "Google oauth sign up");

    private String value;
    private String description;

    AuthProvider(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription(){return this.description;}

    @Override
    public String toString() {
        return this.getValue();
    }

    @JsonCreator
    public static AuthProvider getEnum(String value) {
        for(AuthProvider v : values())
            if(v.getValue().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
