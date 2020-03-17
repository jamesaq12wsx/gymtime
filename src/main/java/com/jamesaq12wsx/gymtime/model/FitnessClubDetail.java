package com.jamesaq12wsx.gymtime.model;

import java.util.List;
import java.util.Map;

public interface FitnessClubDetail extends FitnessClub {

    List<Mark> getMarks();

    // :TODO facilities
    Map<ClubAmenity, String> getAmenities();

}
