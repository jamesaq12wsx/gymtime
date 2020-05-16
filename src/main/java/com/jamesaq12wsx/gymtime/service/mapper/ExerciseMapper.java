package com.jamesaq12wsx.gymtime.service.mapper;

import com.jamesaq12wsx.gymtime.model.entity.Exercise;
import com.jamesaq12wsx.gymtime.model.entity.MuscleGroup;
import com.jamesaq12wsx.gymtime.service.dto.ExerciseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {MuscleGroupMapper.class, MuscleMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExerciseMapper extends BaseMapper<ExerciseDto, Exercise> {

}
