package com.jamesaq12wsx.gymtime.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jamesaq12wsx.gymtime.model.entity.Brand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class FitnessClubDto implements Serializable {

    private Long id;

    private String name;

    private BrandDto brand;

    private double latitude;

    private double longitude;

    private String address;

    private String city;

    private String state;

    private String zipCode;

    private String homeUrl;

    private Map<String,String> openHours;

}
