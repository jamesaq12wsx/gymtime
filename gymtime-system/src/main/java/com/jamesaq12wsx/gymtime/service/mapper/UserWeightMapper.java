package com.jamesaq12wsx.gymtime.service.mapper;

import com.jamesaq12wsx.gymtime.model.entity.UserWeight;
import com.jamesaq12wsx.gymtime.service.dto.UserWeightDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {MeasurementUnitMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserWeightMapper extends BaseMapper<UserWeightDto, UserWeight> {
}
