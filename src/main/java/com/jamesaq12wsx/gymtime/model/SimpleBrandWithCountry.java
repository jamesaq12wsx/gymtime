package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SimpleBrandWithCountry extends SimpleBrand implements BrandWithCountry {

    private String countryName;

    private String alphaTwoCode;

    private String alphaThreeCode;

    private String region;

    private String numericCode;

    private String flagUrl;

    public SimpleBrandWithCountry(int id, String name, int countryId, String icon, String countryName, String alphaTwoCode, String alphaThreeCode, String region, String numericCode, String flagUrl) {
        super(id, name, countryId, icon);
        this.countryName = countryName;
        this.alphaTwoCode = alphaTwoCode;
        this.alphaThreeCode = alphaThreeCode;
        this.region = region;
        this.numericCode = numericCode;
        this.flagUrl = flagUrl;
    }
}
