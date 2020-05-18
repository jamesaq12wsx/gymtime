package com.jamesaq12wsx.gymtime.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
public class CountryDto implements Serializable {

    private int id;

    private String name;

    private String alphaTwoCode;

    private String alphaThreeCode;

    private String region;

    private String numericCode;

    private String flagUrl;

}
