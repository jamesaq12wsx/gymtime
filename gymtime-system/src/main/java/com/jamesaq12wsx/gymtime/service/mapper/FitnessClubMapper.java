package com.jamesaq12wsx.gymtime.service.mapper;

import com.jamesaq12wsx.gymtime.base.BaseMapper;
import com.jamesaq12wsx.gymtime.service.dto.FitnessClubDto;
import com.jamesaq12wsx.gymtime.model.entity.FitnessClub;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {BrandMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FitnessClubMapper extends BaseMapper<FitnessClubDto, FitnessClub> {
}
