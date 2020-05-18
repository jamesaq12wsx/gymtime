package com.jamesaq12wsx.gymtime.service.mapper;

import com.jamesaq12wsx.gymtime.base.BaseMapper;
import com.jamesaq12wsx.gymtime.model.entity.UserUnitSetting;
import com.jamesaq12wsx.gymtime.service.dto.UserUnitSettingDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {MeasurementUnitMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserUnitSettingMapper extends BaseMapper<UserUnitSettingDto, UserUnitSetting> {
}
