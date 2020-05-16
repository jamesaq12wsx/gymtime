package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.model.MeasurementType;
import com.jamesaq12wsx.gymtime.model.entity.MeasurementUnit;
import com.jamesaq12wsx.gymtime.service.dto.MeasurementUnitDto;
import com.jamesaq12wsx.gymtime.service.mapper.MeasurementUnitMapper;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class MapperTest {

    @Test
    public void testMeasurementUnitToDto(){
        MeasurementUnit unit = new MeasurementUnit();
        unit.setMeasurementType(MeasurementType.HEIGHT);

        MeasurementUnitDto unitDto = MeasurementUnitMapper.INSTANCE.toDto(unit);

        assertEquals(unit.getMeasurementType().getValue(), unitDto.getType().toLowerCase());
    }


}
