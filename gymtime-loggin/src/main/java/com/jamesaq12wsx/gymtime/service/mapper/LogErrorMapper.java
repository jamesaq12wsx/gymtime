package com.jamesaq12wsx.gymtime.service.mapper;

import com.jamesaq12wsx.gymtime.base.BaseMapper;
import com.jamesaq12wsx.gymtime.model.entity.Log;
import com.jamesaq12wsx.gymtime.service.dto.LogErrorDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogErrorMapper extends BaseMapper<LogErrorDto, Log> {
}
