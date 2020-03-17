package com.jamesaq12wsx.gymtime.model.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class LaFitnessInfoResponse {

    @JsonProperty("d")
    LaFitnessClubInfo result;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    class LaFitnessClubInfo {
        String clubHours;

        String kidsclubHours;

        String poolAmenityHour;

        String whirlAmenityHours;

        String clubImage;

        boolean findClub;

        String clubHomeURL;
    }
}
