package com.mycompany.test;

import io.github.savatware.mapstruct.addons.spring.MappingMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CarMapper {

    @MappingMetadata("CarMappingMetadata")
    @Mapping(target = "manufacturer", source = "make")
    @Mapping(target = "seatCount", source = "numberOfSeats")
    CarDto carToCarDto(Car car);

    @MappingMetadata("PersonMappingMetadata")
    @Mapping(target = "fullName", source = "name")
    PersonDto personToPersonDto(Person person);

    public static record CarDto(String manufacturer, String seatCount) {}
    public static record Car(String make, String numberOfSeats) {}
    public static record PersonDto(String fullName) {}
    public static record Person(String name) {}
}
