package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LaFitnessClub {

    @JsonProperty("ClubID")
    int clubId;

    @JsonProperty("Longitude")
    double longitude;

    @JsonProperty("Latitude")
    double latitude;

    @JsonProperty("Address")
    String address;

    @JsonProperty("Description")
    String description;

    @JsonProperty("ClubStatus")
    int clubStatus;

    @JsonProperty("IsEsporta")
    boolean isEsporta;

    @JsonProperty("State")
    String state;

    @JsonProperty("City")
    String City;

    @JsonProperty("BrandId")
    int brandId;

    @JsonProperty("Distance")
    int distance;

    @JsonProperty("FacebookURL")
    String facebookUrl;

    @JsonProperty("ClubHomeURL")
    String clubHomeUrl;

}
