package com.jamesaq12wsx.gymtime.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LaFitnessClubsRequest {

    String zipCode;

    String state;

}
