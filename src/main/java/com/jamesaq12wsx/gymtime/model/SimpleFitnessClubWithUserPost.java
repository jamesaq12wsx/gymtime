package com.jamesaq12wsx.gymtime.model;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class SimpleFitnessClubWithUserPost extends SimpleFitnessClub implements FitnessClub {

    List<LocalDateTime> postDataTimeList;

    public SimpleFitnessClubWithUserPost(UUID uuid, int brandId, String brandName, String name, int id, double latitude, double longitude, String address, String city, String state, String zipCode, String country, String homeUrl, Map<String, String> openHours, List<LocalDateTime> postDataTimeList) {
        super(uuid, brandId, brandName, name, id, latitude, longitude, address, city, state, zipCode, country, homeUrl, openHours);
        this.postDataTimeList = postDataTimeList;
    }

    public List<LocalDateTime> getPostDataTimeList() {
        return postDataTimeList == null ? Collections.emptyList() : postDataTimeList;
    }
}
