package com.jamesaq12wsx.gymtime.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jamesaq12wsx.gymtime.base.BaseEntity;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLHStoreType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

@Entity
@Table(name = "fitness_club")
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "hstore", typeClass = PostgreSQLHStoreType.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FitnessClub extends BaseEntity implements Serializable {

    @Id
    @Column(name = "club_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ApiModelProperty(value = "External ID")
    private Long id;

    @Column(name = "club_name")
    @ApiModelProperty(value = "Club Name")
    private String name;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_brand")
    @ApiModelProperty(value = "Club Brand")
    private Brand brand;

    @ApiModelProperty(value = "Club Latitude")
    private double latitude;

    @ApiModelProperty(value = "Club Longitude")
    private double longitude;

    @ApiModelProperty(value = "Address")
    private String address;

    @ApiModelProperty(value = "Club City")
    private String city;

    @ApiModelProperty(value = "Club State")
    private String state;

    @Column(name = "zip_code")
    @ApiModelProperty(value = "Club Zipcode")
    private String zipCode;

    @Column(name = "club_home_url")
    @ApiModelProperty(value = "Club Home URL")
    private String homeUrl;

    @Type(type = "hstore")
    @Column(name = "open_hours", columnDefinition = "hstore")
    @ApiModelProperty(value = "Club Open Hours")
    private Map<String,String> openHours;

}
