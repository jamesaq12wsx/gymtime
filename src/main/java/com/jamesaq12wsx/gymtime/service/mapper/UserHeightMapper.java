package com.jamesaq12wsx.gymtime.service.mapper;

import com.jamesaq12wsx.gymtime.model.entity.UserHeight;
import com.jamesaq12wsx.gymtime.service.dto.UserHeightDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserHeightMapper extends BaseMapper<UserHeightDto, UserHeight> {
}
