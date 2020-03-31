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
public class SimpleBrandAudit extends SimpleBrand implements BrandAudit {

    private String createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public SimpleBrandAudit(int id,
                            String name,
                            int countryId,
                            String icon,
                            String createdBy,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt) {
        super(id, name, countryId, icon);
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
