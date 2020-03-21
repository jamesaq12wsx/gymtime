package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SimpleFitnessClubDetail extends SimpleFitnessClub implements FitnessClubDetail {

    private List<Mark> marks;

    private Map<ClubAmenity, String> amenities;

}
