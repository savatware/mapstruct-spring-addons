package io.github.savatware.mapstruct.addons.spring.processor.pageable;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * A JSR 269 annotation {@link Processor} which generates the implementations of pageable mappers (methods
 * annotated with {@code @PageableMapper}).
 */
@SupportedAnnotationTypes("io.github.savatware.mapstruct.addons.spring.PageableMapper")
public class PageableMapperProcessor extends AbstractProcessor {

    // TODO

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }

}
