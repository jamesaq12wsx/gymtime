package com.jamesaq12wsx.gymtime.service.mapper;

import com.jamesaq12wsx.gymtime.base.BaseMapper;
import com.jamesaq12wsx.gymtime.model.entity.PostRecord;
import com.jamesaq12wsx.gymtime.service.dto.PostRecordDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {PostMapper.class, ExerciseMapper.class, MeasurementUnitMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostRecordMapper extends BaseMapper<PostRecordDto, PostRecord> {
}
