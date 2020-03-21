package com.jamesaq12wsx.gymtime.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface FitnessClub {

    UUID getUuid();

    int getId();

    int getBrandId();

    String getBrandName();

    String getName();

    double getLatitude();

    double getLongitude();

    String getHomeUrl();

    String getAddress();

    String getCity();

    String getState();

    String getZipCode();

    Map<String, String> getOpenHours();

}
