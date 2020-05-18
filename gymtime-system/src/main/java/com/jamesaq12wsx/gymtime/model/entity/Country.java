package com.jamesaq12wsx.gymtime.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "country")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Country extends BaseEntity implements Serializable {

    @Id
    @ApiModelProperty(value = "Country Value")
    private int id;

    private String name;

    @Column(name = "alpha_two_code")
    private String alphaTwoCode;

    @Column(name = "alpha_three_code")
    private String alphaThreeCode;

    private String region;

    @Column(name = "numeric_code")
    private String numericCode;

    @Column(name = "flag_url")
    private String flagUrl;

}
