package com.jamesaq12wsx.gymtime.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class SimpleFitnessClubWithUserPost extends SimpleFitnessClub implements FitnessClub {

    List<LocalDateTime> postDateTimeList;

    public SimpleFitnessClubWithUserPost(UUID uuid, int brandId, String brandName, String name, int id, double latitude, double longitude, String address, String city, String state, String zipCode, String country, String homeUrl, Map<String, String> openHours, List<LocalDateTime> postDateTimeList) {
        super(uuid, brandId, brandName, name, id, latitude, longitude, address, city, state, zipCode, country, homeUrl, openHours);
        this.postDateTimeList = postDateTimeList;
    }

    public List<LocalDateTime> getPostDateTimeList() {
        return postDateTimeList == null ? Collections.emptyList() : postDateTimeList;
    }
}
