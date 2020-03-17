package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LaFitnessClubDetail extends LaFitnessClub implements FitnessClubDetail {

    private List<Mark> marks;

    private Map<ClubAmenity, String> amenities;

    public LaFitnessClubDetail(UUID uuid, int id, double latitude, double longitude, String name, String address, String city, String state, String zipCode, String homeUrl, Map<String, String> openHours, String distance, List<Mark> marks, Map<ClubAmenity, String> amenities) {
        super(uuid, id, longitude, latitude, name, address, city, state, zipCode, homeUrl, openHours, distance);

        setMarks(marks);
        setAmenities(amenities);
    }

    public void setMarks(List<Mark> marks) {
        if(marks != null){
            this.marks = marks;
        }else{
            this.marks = new ArrayList<>();
        }
    }

    public void setAmenities(Map<ClubAmenity, String> amenities) {
        if (amenities != null){
            this.amenities = amenities;
        }else{
            this.amenities = new HashMap<>();
        }
    }
}
