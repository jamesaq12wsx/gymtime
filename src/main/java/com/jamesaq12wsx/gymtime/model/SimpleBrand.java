package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jamesaq12wsx.gymtime.model.entity.Audit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fitness_brand")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SimpleBrand implements Brand, Auditable {

    @Id
    @Column(name = "id")
    private int brandId;

    @Column(name = "name")
    private String brandName;

//    @Column(name = "country")
//    private int countryId;

    private String icon;

//    @Column(name = "created_by")
//    private String createdBy;
//
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;

    @Embedded
    private Audit audit;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="country")
    private SimpleCountry country;


    @Override
    public int getCountryId() {
        return country.getId();
    }

}
