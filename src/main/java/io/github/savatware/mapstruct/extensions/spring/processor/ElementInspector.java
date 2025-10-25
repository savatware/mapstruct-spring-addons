package io.github.savatware.mapstruct.extensions.spring.processor;

import io.github.savatware.mapstruct.extensions.spring.MappingMetadata;
import org.mapstruct.Mapping;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ElementInspector {

    private final ProcessingEnvironment processingEnv;

    public ElementInspector(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public Optional<String> getClassName(Element element) {
        var actualAnnotation = element.getAnnotation(MappingMetadata.class);
        var className = actualAnnotation.value().trim();

        if (className.contains(".")) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Class name can not contain a dot, received: " + className);
            return Optional.empty();
        }

        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, " *** Class: " + className);
        return Optional.of(className);
    }

    public List<MappingValues> getMappings(Element element) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, " *** Mapping Metadata");
        var mappingAnnotations = element.getAnnotationsByType(Mapping.class);
        var mappings = new ArrayList<MappingValues>();

        Arrays.stream(mappingAnnotations).forEach(mappingAnnotation -> {
            if (isNotBlank(mappingAnnotation.source()) && isNotBlank(mappingAnnotation.target())) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, " *** " + mappingAnnotation.source() + " - " + mappingAnnotation.target());
                mappings.add(new MappingValues(mappingAnnotation.source(), mappingAnnotation.target(), mappingAnnotation.qualifiedByName()));
            }
        });

        return mappings;
    }

    public String getPackageName(Element element) {
        var classElement = element.getEnclosingElement();
        var packageElement = classElement.getEnclosingElement();
        var packageFullyQualifiedName = "";
        if (packageElement instanceof PackageElement pe) {
            packageFullyQualifiedName = pe.getQualifiedName().toString();
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, " *** Package: " + packageFullyQualifiedName);
        } else {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, " *** No package found, but: " + packageElement.getSimpleName());
        }
        return packageFullyQualifiedName;
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.isEmpty();
    }

}
