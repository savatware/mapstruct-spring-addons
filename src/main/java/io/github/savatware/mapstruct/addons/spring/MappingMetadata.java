package io.github.savatware.mapstruct.addons.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 *  Usage: specify the name of the class to generate
 *  e.g. @MappingMetadata("MyDtoMetadata")
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MappingMetadata {

     /** Name of the generated class, make sure to use a unique name. */
    String value();

}

