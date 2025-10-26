package com.mycompany.test;

import io.github.savatware.mapstruct.addons.spring.MappingMetadata;
import org.mapstruct.Mapper;

@Mapper
public abstract class MyMapper {

    @MappingMetadata("MyMappingMetadata")
    public abstract MyDto toDto(MyEntity entity);

    public static record MyDto() {}
    public static record MyEntity() {}

}
