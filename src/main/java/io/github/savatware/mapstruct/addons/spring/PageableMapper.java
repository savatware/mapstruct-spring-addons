package io.github.savatware.mapstruct.addons.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PageableMapper {

     /** Name of the generated class. When using this annotation multiple times in your project, make sure to use a unique name. */
    String value();

}

