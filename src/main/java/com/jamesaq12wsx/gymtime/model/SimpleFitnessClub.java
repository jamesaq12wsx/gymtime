package com.jamesaq12wsx.gymtime.model;

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
public class SimpleFitnessClub implements FitnessClub, Serializable {

    private UUID uuid;

    private int brandId;

    private String brandName;

    private String name;

    private int id;

    private double latitude;

    private double longitude;

    private String address;

    private String city;

    private String state;

    private String zipCode;

    private String country;

    private String homeUrl;

    private Map<String,String> openHours;

}
