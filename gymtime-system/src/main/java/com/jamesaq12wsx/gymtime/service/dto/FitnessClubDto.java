package com.jamesaq12wsx.gymtime.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
public class FitnessClubDto implements Serializable {

    private Long id;

    private String name;

    private BrandDto brand;

    private double latitude;

    private double longitude;

    private String address;

    private String city;

    private String state;

    private String zipCode;

    private String homeUrl;

    private Map<String,String> openHours;

}
