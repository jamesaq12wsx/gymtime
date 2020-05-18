package com.jamesaq12wsx.gymtime.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "fitness_brand")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Brand extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "Brand Id", hidden = true)
    private int id;

    @Column(name = "name")
    @ApiModelProperty(value = "Brand Name")
    private String brandName;

    @ApiModelProperty(value = "Brand Icon Image")
    private String icon;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="country")
    private Country country;

}
