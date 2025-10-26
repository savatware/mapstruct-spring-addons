# MapStruct Spring Addons 🚀

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Introduction

This library contains additions to MapStruct when working with the Spring Framework.  

These additions provide functionality which is not available in [MapStruct](https://github.com/mapstruct/mapstruct), 
and also not available in [MapStruct Spring Extensions](https://github.com/mapstruct/mapstruct-spring-extensions).

#### Table of Contents
<!-- TOC -->
* [Set up](#set-up)
  * [Maven](#maven)
* [Features](#features)
  * [Mapping Metadata](#mapping-metadata)
  * [Pageable Support](#pageable-support)
<!-- TOC -->

## Set up

### Maven
TODO maven setup

## Features

### Mapping Metadata
If you want to know at runtime which MapStruct `@Mapping` annotations are defined, then you have a problem.
Because these annotations have a retention setting that don't allow you to use reflection at runtime.

The `@MappingMetadata` annotation fixes this issue. When declaring it on a mapping method, then 
a source file will be generated describing the mapping annotations on that method.

For example:

```java
@Mapper
public interface CarMapper {

  @MappingMetadata("CarMappingMetadata")
  @Mapping(target = "manufacturer", source = "make")
  @Mapping(target = "seatCount", source = "numberOfSeats")
  CarDto carToCarDto(Car car);

  @MappingMetadata("PersonMappingMetadata")
  @Mapping(target = "fullName", source = "name")
  PersonDto personToPersonDto(Person person);
  
}
```
The `@MappingMetadata` annotation causes the code generator to create an implementation of the 
`MappingMetadataLookup` interface during build-time.  

To get a better understanding of what the code generation does, have a look at the following implementation:
```java
@Component
public class CarMappingMetadata implements MappingMetadataLookup {

  private final List<MappingDescription> mappingDescriptions = new ArrayList<>();

  public CarMappingMetadata() {
    mappingDescriptions.add(new MappingDescription("make", "manufacturer", "", "", "", "", "", false, new String[] {}, new String[] {}, "", new String[] {}, ""));
    mappingDescriptions.add(new MappingDescription("numberOfSeats", "seatCount", "", "", "", "", "", false, new String[] {}, new String[] {}, "", new String[] {}, ""));
  }

  @Override
  public List<MappingDescription> getMappingDescriptions() {
    return mappingDescriptions;
  }

}
```
Since the implementation is tagged as a SpringBean using `@Component`, you can autowire the dependency
to access the mapping metadata. Use the name of the class in camelCase as a qualifier for injection.
For example:
```java
@Service
public class CarService {
    
    @Autowired
    @Qualifier("carMappingMetadata")
    private MappingMetadataLookup carMapperMetadata;

    @Autowired
    private final CarMapper carMapper;
    
}
```

> [!TIP]  
> Example use-case: Use this annotation to convert a sort field as known by a client
> to the property known in your model

### Pageable Support
TODO
