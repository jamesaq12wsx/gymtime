package com.jamesaq12wsx.gymtime.service.mapper;

import com.jamesaq12wsx.gymtime.model.entity.Muscle;
import com.jamesaq12wsx.gymtime.service.dto.MuscleDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MuscleMapper extends BaseMapper<MuscleDto, Muscle> {
}
