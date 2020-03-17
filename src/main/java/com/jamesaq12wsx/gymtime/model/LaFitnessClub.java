package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LaFitnessClub implements FitnessClub, Serializable {

    private UUID uuid;

    private final GymBrand brand = GymBrand.LA_FITNESS;

    private int id;

    private double longitude;

    private double latitude;

    private String name;

    private String address;

    private String city;

    private String state;

    private String zipCode;

    private String homeUrl;

    private Map<String,String> openHours;

    private String distance;

    @Override
    public GymBrand getBrand() {
        return brand;
    }
}
