package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SimpleFitnessClub implements FitnessClub, Serializable {

    private UUID clubUuid;

    private String clubName;

    private int clubId;

    private double latitude;

    private double longitude;

    private String address;

    private String city;

    private String state;

    private String zipCode;

    private String homeUrl;

    private Map<String,String> openHours;

}
