package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.jamesaq12wsx.gymtime.auth.AuthProvider;

public enum PostPrivacy {
    PUBLIC("public"),
    PRIVATE("private");

    private String value;

    PostPrivacy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    @JsonCreator
    public static PostPrivacy getEnum(String value) {
        for(PostPrivacy v : values())
            if(v.getValue().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
