package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SimpleFitnessClubSelectItem implements FitnessClubSelectItem {

    private UUID clubUuid;

    private String name;

    private String brand;

    private String country;

    private String state;

    private String city;

}
