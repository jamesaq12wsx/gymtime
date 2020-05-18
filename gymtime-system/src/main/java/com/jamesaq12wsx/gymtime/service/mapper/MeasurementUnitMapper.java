package com.jamesaq12wsx.gymtime.service.mapper;

import com.jamesaq12wsx.gymtime.model.entity.MeasurementUnit;
import com.jamesaq12wsx.gymtime.service.dto.MeasurementUnitDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MeasurementUnitMapper extends BaseMapper<MeasurementUnitDto, MeasurementUnit> {

    MeasurementUnitMapper INSTANCE = Mappers.getMapper( MeasurementUnitMapper.class );

    @Override
    @Mapping(source = "measurementType", target = "type")
    MeasurementUnitDto toDto(MeasurementUnit entity);

}
