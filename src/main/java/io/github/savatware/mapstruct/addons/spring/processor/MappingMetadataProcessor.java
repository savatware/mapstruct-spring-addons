package io.github.savatware.mapstruct.addons.spring.processor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import static io.github.savatware.mapstruct.addons.spring.processor.GeneratedFileWriter.writeFile;
import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.Diagnostic.Kind.NOTE;

/**
 * A JSR 269 annotation {@link Processor} which generates the implementations of metadata for mappings (methods
 * annotated with {@code @MappingMetadata}).
 */
@SupportedAnnotationTypes("io.github.savatware.mapstruct.addons.spring.MappingMetadata")
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
                processingEnv.getMessager().printMessage(NOTE, "Processing mapping metadata for " + element.getSimpleName());

                var attributes = new MappingAnnotationAttributes(elementInspector, element, getDateTime());
                if (hasInvalidAttributes(attributes, elementInspector, element)) {
                    continue;
                }
                generate(attributes, generatedClasses);
            }
        }
        return true; // No further processing of this annotation
    }

    void setDateTime(ZonedDateTime dateTime) {
        now = dateTime;
    }

    private boolean hasInvalidAttributes(MappingAnnotationAttributes attributes, ElementInspector elementInspector, Element element) {
        if (attributes.getClassName().contains(".")) {
            var containingClass = elementInspector.getContainingClass(element);
            processingEnv.getMessager().printMessage(ERROR,
                    "Class name can not contain a dot, received: " + attributes.getClassName() + ", defined in \"" + containingClass + "\"");
            return true;
        }

        if (attributes.getClassName().isEmpty()) {
            var containingClass = elementInspector.getContainingClass(element);
            processingEnv.getMessager().printMessage(ERROR,
                    "Not allowed to define an empty classname, defined in \"" + containingClass + "\"");
            return true;
        }

        return false;
    }

    private void generate(MappingAnnotationAttributes attributes, Set<String> generatedClasses) {
        try {
            var fqdn = attributes.getFullyQualifiedName();
            if (generatedClasses.contains(fqdn)) {
                processingEnv.getMessager().printMessage(ERROR, "Found duplicate class \"" + fqdn + "\"");
                return;
            }
            generatedClasses.add(fqdn);

            var filer = processingEnv.getFiler();
            var fileObject = filer.createSourceFile(fqdn);
            try (var writer = fileObject.openWriter()) {
                writeFile(writer, attributes);
            }
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(ERROR, "Failed to generate class: " + e.getMessage());
        }
    }
}

