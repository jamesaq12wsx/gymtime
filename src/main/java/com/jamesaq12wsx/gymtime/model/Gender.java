package com.jamesaq12wsx.gymtime.model;

import com.jamesaq12wsx.gymtime.auth.AuthProvider;

public enum Gender {

    MALE("male"),
    FEMALE("female");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static Gender getEnum(String value) {
        for(Gender v : values())
            if(v.getValue().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }

}
