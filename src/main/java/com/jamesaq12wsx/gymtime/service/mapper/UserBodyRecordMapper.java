package com.jamesaq12wsx.gymtime.service.mapper;

import com.jamesaq12wsx.gymtime.model.entity.UserBodyRecord;
import com.jamesaq12wsx.gymtime.service.dto.UserBodyRecordDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {UserHeightMapper.class, UserWeightMapper.class, UserBodyFatMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserBodyRecordMapper extends BaseMapper<UserBodyRecordDto, UserBodyRecord> {
}
