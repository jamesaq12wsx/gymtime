package com.jamesaq12wsx.gymtime.service.mapper;

import com.jamesaq12wsx.gymtime.model.entity.Country;
import com.jamesaq12wsx.gymtime.service.dto.CountryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountryMapper extends BaseMapper<CountryDto, Country> {
}
