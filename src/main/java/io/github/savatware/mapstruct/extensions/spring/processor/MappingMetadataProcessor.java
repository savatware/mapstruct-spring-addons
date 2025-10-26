package io.github.savatware.mapstruct.extensions.spring.processor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static io.github.savatware.mapstruct.extensions.spring.processor.GeneratedFileWriter.writeFile;

/**
 * A JSR 269 annotation {@link Processor} which generates the implementations of metadata for mappings (methods
 * annotated with {@code @MappingMetadata}).
 */
@SupportedAnnotationTypes("io.github.savatware.mapstruct.extensions.spring.MappingMetadata")
public class MappingMetadataProcessor extends AbstractProcessor {

    private ZonedDateTime now;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    void setDateTime(ZonedDateTime dateTime) {
        now = dateTime;
    }

    public ZonedDateTime getDateTime() {
        if (now == null) {
            now = ZonedDateTime.now();
        }
        return now;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        var generatedClasses = new HashSet<>();
        var elementInspector = new ElementInspector(processingEnv);
        for (var annotation : annotations) {
            for (var element : roundEnv.getElementsAnnotatedWith(annotation)) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing mapping metadata for " + element.getSimpleName());

                // TODO cleanup by combining properties in a class
                var packageFullyQualifiedName = elementInspector.getPackageName(element);
                var className = elementInspector.getClassName(element).orElse("");
                var mappings = elementInspector.getMappings(element);
                var dateTime = getDateTime();

                if (className.contains(".")) {
                    var containingClass = elementInspector.getContainingClass(element);
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                            "Class name can not contain a dot, received: " + className + ", defined in \"" + containingClass + "\"");
                    continue; // skip invalid class names
                }

                if (className.isEmpty()) {
                    var containingClass = elementInspector.getContainingClass(element);
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                            "Skipping creation for requested empty classname, defined in \"" + containingClass + "\"");
                    continue; // skip invalid class names
                }

                try {
                    var fqdn = packageFullyQualifiedName + '.' + className;
                    if (generatedClasses.contains(fqdn)) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Skipping creation for duplicate class, received: " + fqdn);
                        continue;
                    }
                    generatedClasses.add(fqdn);

                    var filer = processingEnv.getFiler();
                    var fileObject = filer.createSourceFile(fqdn);
                    try (var writer = fileObject.openWriter()) {
                        writeFile(writer, packageFullyQualifiedName, className, mappings, dateTime);
                    }
                } catch (IOException e) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to generate class: " + e.getMessage());
                }
            }
        }
        return true; // No further processing of this annotation
    }

}

