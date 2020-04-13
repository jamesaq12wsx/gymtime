package com.jamesaq12wsx.gymtime.auth;

public enum AuthProvider {

    LOCAL("local"),
    FACEBOOK("facebook"),
    GOOGLE("google");

    private String value;

    AuthProvider(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static AuthProvider getEnum(String value) {
        for(AuthProvider v : values())
            if(v.getValue().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
