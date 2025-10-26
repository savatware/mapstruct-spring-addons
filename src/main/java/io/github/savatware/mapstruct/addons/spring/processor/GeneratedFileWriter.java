package io.github.savatware.mapstruct.addons.spring.processor;


import io.github.savatware.mapstruct.addons.spring.MappingDescription;

import java.io.IOException;
import java.io.Writer;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GeneratedFileWriter {

    private static final String INDENT = "  ";

    public static void writeFile(Writer writer, MappingMetadataProcessor.Fields fields) throws IOException {
        writePackage(writer, fields.getPackageName());
        writeImports(writer);
        writeGeneratedAnnotation(writer, fields.getDateTime());
        writeSpringStereotypeAnnotation(writer);
        writeClassStart(writer, fields.getClassName());
        writeConstructorAndMappings(fields.getMappings(), writer, fields.getClassName());
        writeMappingMethods(writer);
        writeClassEnd(writer);
    }

    private static void writeClassEnd(Writer writer) throws IOException {
        writer.write("\n");
        writer.write("}\n");
    }

    private static void writeClassStart(Writer writer, String className) throws IOException {
        writer.write("public class " + className + " implements MappingMetadataLookup {\n");
        writer.write("\n");
    }

    // TODO only add if the component type is Spring
    private static void writeSpringStereotypeAnnotation(Writer writer) throws IOException {
        writer.write("@Component\n");
    }

    private static void writePackage(Writer writer, String packageFullyQualifiedName) throws IOException {
        writer.write("package " + packageFullyQualifiedName + ";\n");
        writer.write("\n");
    }

    private static void writeConstructorAndMappings(List<MappingDescription> mappings, Writer writer, String className) throws IOException {
        writer.write(INDENT + "private final List<MappingDescription> mappingDescriptions = new ArrayList<>();\n");
        writer.write("\n");
        writer.write(INDENT + "public " + className + "() {\n");

        for (var mapping : mappings) {
            writer.write(INDENT + INDENT + "mappingDescriptions.add(new MappingDescription(" +
                    "\"" + mapping.source() + "\", " +
                    "\"" + mapping.target() + "\", " +
                    "\"" + mapping.dateFormat() + "\", " +
                    "\"" + mapping.numberFormat() + "\", " +
                    "\"" + mapping.constant() + "\", " +
                    "\"" + mapping.expression() + "\", " +
                    "\"" + mapping.defaultExpression() + "\", " +
                    mapping.ignore() + ", " +
                    toStringArray(mapping.qualifiedByName()) + ", " +
                    toStringArray(mapping.conditionQualifiedByName()) + ", " +
                    "\"" + mapping.conditionExpression() + "\", " +
                    toStringArray(mapping.dependsOn()) + ", " +
                    "\"" + mapping.defaultValue() + "\"" +
                    "));\n");
        }
        writer.write(INDENT + "}\n");
        writer.write("\n");
    }

    private static String toStringArray(String[] values) {
        var valuesAsString = Arrays.stream(values)
                .map(value -> "\"" + value + "\"")
                .collect(Collectors.joining(", "));
        return "new String[] {" + valuesAsString + "}";
    }

    private static void writeGeneratedAnnotation(Writer writer, ZonedDateTime dateTime) throws IOException {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        var timestamp = formatter.format(dateTime);
        writer.write("@Generated(\n");
        writer.write(INDENT + "value = \"" + MappingMetadataProcessor.class.getName() + "\",\n");
        writer.write(INDENT + "date = \"" + timestamp + "\"\n");
        writer.write(")\n");
    }

    private static void writeImports(Writer writer) throws IOException {
        writer.write("import javax.annotation.processing.Generated;\n");
        writer.write("import org.springframework.stereotype.Component;\n");
        writer.write("import java.util.List;\n");
        writer.write("import java.util.ArrayList;\n");
        writer.write("import io.github.savatware.mapstruct.addons.spring.MappingMetadataLookup;\n");
        writer.write("import io.github.savatware.mapstruct.addons.spring.MappingDescription;\n");
        writer.write("\n");
    }

    private static void writeMappingMethods(Writer writer) throws IOException {
        writer.write(INDENT + "@Override\n");
        writer.write(INDENT + "public List<MappingDescription> getMappingDescriptions() {\n");
        writer.write(INDENT + INDENT + "return mappingDescriptions;\n");
        writer.write(INDENT + "}\n");
    }

}
