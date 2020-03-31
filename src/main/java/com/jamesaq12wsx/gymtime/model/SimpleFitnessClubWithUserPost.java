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
public class SimpleFitnessClubWithUserPost extends SimpleFitnessClubWithBrandAndCountry implements FitnessClub {

    private List<LocalDateTime> postDateTimeList;

    public SimpleFitnessClubWithUserPost(List<LocalDateTime> postDateTimeList) {
        this.postDateTimeList = postDateTimeList;
    }

    public SimpleFitnessClubWithUserPost(UUID clubUuid, String clubName, int clubId, double latitude, double longitude, String address, String city, String state, String zipCode, String homeUrl, Map<String, String> openHours, int brandId, String brandName, int countryId, String countryName, String alphaTwoCode, String alphaThreeCode, String region, String numericCode, String flagUrl, String icon, List<LocalDateTime> postDateTimeList) {
        super(clubUuid, clubName, clubId, latitude, longitude, address, city, state, zipCode, homeUrl, openHours, brandId, brandName, icon, countryId, countryName, alphaTwoCode, alphaThreeCode, region, numericCode, flagUrl);
        this.postDateTimeList = postDateTimeList;
    }

    public List<LocalDateTime> getPostDateTimeList() {
        return postDateTimeList == null ? Collections.emptyList() : postDateTimeList;
    }
}
