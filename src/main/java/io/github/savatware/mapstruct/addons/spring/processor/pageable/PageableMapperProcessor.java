package io.github.savatware.mapstruct.addons.spring.processor.pageable;

import io.github.savatware.mapstruct.addons.spring.processor.ElementInspector;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.tools.Diagnostic.Kind.NOTE;

/**
 * A JSR 269 annotation {@link Processor} which generates the implementations of pageable mappers (methods
 * annotated with {@code @PageableMapper}).
 */
@SupportedAnnotationTypes("io.github.savatware.mapstruct.addons.spring.PageableMapper")
public class PageableMapperProcessor extends AbstractProcessor {

    private ZonedDateTime now;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    public ZonedDateTime getDateTime() {
        if (now == null) {
            now = ZonedDateTime.now();
        }
        return now;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        var generatedClasses = new HashSet<String>();
        var elementInspector = new ElementInspector(processingEnv);
        for (var annotation : annotations) {
            for (var element : roundEnv.getElementsAnnotatedWith(annotation)) {
                processingEnv.getMessager().printMessage(NOTE, "Processing pageable mapper for " + element.getSimpleName());
                // TODO
            }
        }
        return true; // No further processing of this annotation
    }

    void setDateTime(ZonedDateTime dateTime) {
        now = dateTime;
    }
}
