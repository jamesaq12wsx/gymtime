package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MeasurementType {

    DISTANCE("distance", "Distance unit"),
    HEIGHT("height", "Height unit"),
    WEIGHT("weight", "Weight unit"),
    DURATION("duration", "Duration unit");

    private String value;
    private String description;

    MeasurementType(String value, String description) {
        this.value = value;
        this.description = description;
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
