package io.github.savatware.mapstruct.extensions.spring.processor;

import io.github.savatware.mapstruct.extensions.spring.MappingMetadata;
import org.mapstruct.Mapping;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

import static io.github.savatware.mapstruct.extensions.spring.processor.GeneratedFileWriter.writeFile;

/**
 * A JSR 269 annotation {@link Processor} which generates the implementations of metadata for mappings (methods
 * annotated with {@code @MappingMetadata}).
 *
 * @author Gunnar Morling
 */
@SupportedAnnotationTypes("io.github.savatware.mapstruct.extensions.spring.MappingMetadata")
public class MappingMetadataProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        var generatedClasses = new HashSet<>();
        for (var annotation : annotations) {
            for (var element : roundEnv.getElementsAnnotatedWith(annotation)) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing mapping metadata for " + element.getSimpleName());
                var packageFullyQualifiedName = getPackageName(element);
                var className = getClassName(element).orElse("");
                var mappings = getMappings(element);
                var filer = processingEnv.getFiler();
                var now = ZonedDateTime.now();

                if (className.isEmpty()) {
                    continue; // skip invalid class names
                }

                try {
                    var fqdn = packageFullyQualifiedName + '.' + className;
                    if (generatedClasses.contains(fqdn)) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Skipping creation for duplicate class, received: " + fqdn);
                        continue;
                    }
                    generatedClasses.add(fqdn);

                    var fileObject = filer.createSourceFile(packageFullyQualifiedName + "." + className);
                    try (var writer = fileObject.openWriter()) {
                        writeFile(writer, packageFullyQualifiedName, className, mappings, now);
                    }
                } catch (IOException e) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to generate class: " + e.getMessage());
                }
            }
        }
        return true; // No further processing of this annotation
    }

    private Optional<String> getClassName(Element element) {
        var actualAnnotation = element.getAnnotation(MappingMetadata.class);
        var className = actualAnnotation.value().trim();

        if (className.contains(".")) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Class name can not contain a dot, received: " + className);
            return Optional.empty();
        }

        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, " *** Class: " + className);
        return Optional.of(className);
    }

    private List<MappingValues> getMappings(Element element) {
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

    private boolean isNotBlank(String value) {
        return value != null && !value.isEmpty();
    }

    private String getPackageName(Element element) {
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

}

