package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.jamesaq12wsx.gymtime.auth.AuthProvider;
import lombok.Setter;

public enum MeasurementType {

    DISTANCE("distance"),
    HEIGHT("height"),
    WEIGHT("weight"),
    DURATION("duration");

    private String value;

    MeasurementType(String value) {
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
    public static MeasurementType getEnum(String value) {
        for(MeasurementType v : values())
            if(v.getValue().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }

}
