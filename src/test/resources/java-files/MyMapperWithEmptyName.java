package com.mycompany.test;

import io.github.savatware.mapstruct.extensions.spring.MappingMetadata;
import org.mapstruct.Mapper;

@Mapper
public abstract class MyMapperWithEmptyName {

    // The empty name should fail compilation
    @MappingMetadata("")
    public abstract MyDto toDto(MyEntity entity);

    public static record MyDto() {}
    public static record MyEntity() {}

}
