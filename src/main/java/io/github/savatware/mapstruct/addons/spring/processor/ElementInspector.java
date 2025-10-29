package io.github.savatware.mapstruct.addons.spring.processor;

import io.github.savatware.mapstruct.addons.spring.MappingMetadataLookup.MappingDescription;
import io.github.savatware.mapstruct.addons.spring.MappingMetadata;
import org.mapstruct.Mapping;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.tools.Diagnostic;
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
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, " *** Class: " + className);
        return Optional.of(className);
    }

    public List<MappingDescription> getMappings(Element element) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, " *** Mapping Metadata");
        var mappingAnnotations = element.getAnnotationsByType(Mapping.class);
        return Arrays.stream(mappingAnnotations)
                .map(this::createMappingDescription)
                .toList();
    }

    public String getContainingClass(Element element) {
        var classElement = element.getEnclosingElement();
        return classElement.getSimpleName().toString();
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

    private MappingDescription createMappingDescription(Mapping mappingAnnotation) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, " *** " + mappingAnnotation.source() + " - " + mappingAnnotation.target());
        return new MappingDescription(
                mappingAnnotation.source(),
                mappingAnnotation.target(),
                mappingAnnotation.dateFormat(),
                mappingAnnotation.numberFormat(),
                mappingAnnotation.constant(),
                mappingAnnotation.expression(),
                mappingAnnotation.defaultExpression(),
                mappingAnnotation.ignore(),
                mappingAnnotation.qualifiedByName(),
                mappingAnnotation.conditionQualifiedByName(),
                mappingAnnotation.conditionExpression(),
                mappingAnnotation.dependsOn(),
                mappingAnnotation.defaultValue()
        );
    }

}
