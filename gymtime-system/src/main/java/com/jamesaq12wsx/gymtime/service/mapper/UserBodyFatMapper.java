package com.jamesaq12wsx.gymtime.service.mapper;

import com.jamesaq12wsx.gymtime.base.BaseMapper;
import com.jamesaq12wsx.gymtime.model.entity.UserBodyFat;
import com.jamesaq12wsx.gymtime.service.dto.UserBodyFatDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserBodyFatMapper extends BaseMapper<UserBodyFatDto, UserBodyFat> {
}
