package io.github.savatware.mapstruct.addons.spring.processor.metadata;

import io.github.savatware.mapstruct.addons.spring.MappingMetadataLookup.MappingDescription;
import io.github.savatware.mapstruct.addons.spring.processor.ElementInspector;

import javax.lang.model.element.Element;
import java.time.ZonedDateTime;
import java.util.List;

public class MappingAnnotationAttributes {

    private final String packageName;
    private final String className;
    private final List<MappingDescription> mappings;
    private final ZonedDateTime dateTime;

    public MappingAnnotationAttributes(ElementInspector inspector, Element element, ZonedDateTime dateTime) {
        this.packageName = inspector.getPackageName(element);
        this.className = inspector.getClassName(element).orElse("");
        this.mappings = inspector.getMappings(element);
        this.dateTime = dateTime;
    }

    public boolean hasPackageName() {
        return packageName != null && !packageName.isEmpty();
    }

    public String getFullyQualifiedName() {
        if (!hasPackageName()) {
            return className;
        }
        return packageName + "." + className;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public List<MappingDescription> getMappings() {
        return mappings;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

}
