package com.jamesaq12wsx.gymtime.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jamesaq12wsx.gymtime.model.entity.Country;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
public class BrandDto implements Serializable {

    private int id;

    private String brandName;

    private String icon;

    private CountryDto country;

}
