package com.jamesaq12wsx.gymtime.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BrandDto implements Serializable {

    private int id;

    private String brandName;

    private String icon;

    private CountryDto country;

}
