package com.jamesaq12wsx.gymtime.service.mapper;

import com.jamesaq12wsx.gymtime.model.entity.MuscleGroup;
import com.jamesaq12wsx.gymtime.service.dto.MuscleGroupDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MuscleGroupMapper extends BaseMapper<MuscleGroupDto, MuscleGroup> {
}
