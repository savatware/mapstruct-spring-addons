package io.github.savatware.mapstruct.extensions.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 *  * Usage: specify the name of the class to generate
 *  * e.g.
 *  *   @MappingMetadata("MyDtoMetadata")
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MappingMetadata {

     /** Name of the generated class. When using this annotation multiple times in your project, then make sure to add a unique name. */

    String value() default "MappingMetadataLookupImpl";
}

