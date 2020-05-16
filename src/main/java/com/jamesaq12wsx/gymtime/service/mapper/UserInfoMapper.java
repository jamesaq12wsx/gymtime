package com.jamesaq12wsx.gymtime.service.mapper;

import com.jamesaq12wsx.gymtime.model.entity.UserInfo;
import com.jamesaq12wsx.gymtime.service.dto.UserInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserInfoMapper extends BaseMapper<UserInfoDto, UserInfo> {

}
