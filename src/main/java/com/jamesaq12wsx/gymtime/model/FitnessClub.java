package com.jamesaq12wsx.gymtime.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface FitnessClub {

    UUID getUuid();

    int getId();

    GymBrand getBrand();

    String getName();

    double getLatitude();

    double getLongitude();

    String getHomeUrl();

    String getAddress();

    String getCity();

    String getState();

    String getZipCode();

    Map<String, String> getOpenHours();

    String getDistance();

    void setUuid(UUID uuid);

    void setId(int id);

    void setName(String name);

    void setLatitude(double latitude);

    void setLongitude(double longitude);

    void setHomeUrl(String homeUrl);

    void setAddress(String address);

    void setCity(String city);

    void setState(String state);

    void setZipCode(String zipCode);

    void setOpenHours(Map<String, String> openHours);

    void setDistance(String distance);

}
