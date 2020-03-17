package com.jamesaq12wsx.gymtime.model.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jamesaq12wsx.gymtime.model.LaFitnessClub;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LAFitnessClubsResponse {

    @JsonProperty("d")
    List<LaFitnessClub> clubs;

}
