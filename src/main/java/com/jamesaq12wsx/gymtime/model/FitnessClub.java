package com.jamesaq12wsx.gymtime.model;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface FitnessClub {

    UUID getClubUuid();

    int getClubId();

    String getClubName();

    double getLatitude();

    double getLongitude();

    String getHomeUrl();

    String getAddress();

    String getCity();

    String getState();

    String getZipCode();

    Map<String, String> getOpenHours();

}
