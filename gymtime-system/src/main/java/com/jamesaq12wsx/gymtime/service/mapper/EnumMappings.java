package com.jamesaq12wsx.gymtime.service.mapper;

import org.mapstruct.MapperConfig;

@MapperConfig
public class EnumMappings {

    protected String mapEnum(Enum<?> e){
        if(e == null) return null;

        return e.toString();
    }

}
