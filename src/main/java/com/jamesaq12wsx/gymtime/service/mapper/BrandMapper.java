package com.jamesaq12wsx.gymtime.service.mapper;

import com.jamesaq12wsx.gymtime.model.entity.Brand;
import com.jamesaq12wsx.gymtime.service.dto.BrandDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {CountryMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandMapper extends BaseMapper<BrandDto, Brand> {
}
