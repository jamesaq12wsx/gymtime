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
public class SimpleCountry {

    private int id;

    private String name;

    private String alphaTwoCode;

    private String alphaThreeCode;

    private String region;

    private String numericCode;

    private String flagUrl;

}
