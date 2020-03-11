package com.jamesaq12wsx.gymtime.model;

import java.util.List;

public interface FitnessClub {

    GymBrand getBrand();

    int getClubId();

    String getName();

    double getLatitude();

    double getLongitude();

    String getAddress();

    String getNumber();

    List<OpenHour> getOpenHours();

    String getUrlLink();

}
