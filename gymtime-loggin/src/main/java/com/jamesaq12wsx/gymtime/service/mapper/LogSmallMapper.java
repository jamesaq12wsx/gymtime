package com.jamesaq12wsx.gymtime.service.mapper;

import com.jamesaq12wsx.gymtime.base.BaseMapper;
import com.jamesaq12wsx.gymtime.model.entity.Log;
import com.jamesaq12wsx.gymtime.service.dto.LogSmallDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogSmallMapper extends BaseMapper<LogSmallDto, Log> {
}
