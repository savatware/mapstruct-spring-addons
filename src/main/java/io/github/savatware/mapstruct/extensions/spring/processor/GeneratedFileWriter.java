package io.github.savatware.mapstruct.extensions.spring.processor;


import java.io.IOException;
import java.io.Writer;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GeneratedFileWriter {

    private static final String INDENT = "  ";

    public static void writeFile(Writer writer, String packageFullyQualifiedName, String className, List<MappingValues> mappings, ZonedDateTime dateTime) throws IOException {
        writePackage(writer, packageFullyQualifiedName);
        writeImports(writer);
        writeGeneratedAnnotation(writer, dateTime);
        writeSpringStereotypeAnnotation(writer);
        writeClassStart(writer, className);
        writeConstructorAndMappings(mappings, writer, className);
        writeMappingMethods(writer);
        writeClassEnd(writer);
    }

    public static void writeClassEnd(Writer writer) throws IOException {
        writer.write("\n");
        writer.write("}\n");
    }

    public static void writeClassStart(Writer writer, String className) throws IOException {
        writer.write("public class " + className + " implements MappingMetadataLookup {\n");
        writer.write("\n");
    }

    // TODO only add if the component type is Spring
    public static void writeSpringStereotypeAnnotation(Writer writer) throws IOException {
        writer.write("@Component\n");
    }

    public static void writePackage(Writer writer, String packageFullyQualifiedName) throws IOException {
        writer.write("package " + packageFullyQualifiedName + ";\n");
        writer.write("\n");
    }

    public static void writeConstructorAndMappings(List<MappingValues> mappings, Writer writer, String className) throws IOException {
        writer.write(INDENT + "private final Map<String, String> mapSourceToTarget = new HashMap<>();\n");
        writer.write(INDENT + "private final Map<String, String> mapTargetToSource = new HashMap<>();\n");
        writer.write("\n");
        writer.write(INDENT + "public " + className + "() {\n");

        for (var mapping : mappings) {
            writer.write(INDENT + INDENT + "mapSourceToTarget.put(\"" + mapping.source() + "\", \"" + mapping.target() + "\");\n");
            writer.write(INDENT + INDENT + "mapTargetToSource.put(\"" + mapping.target() + "\", \"" + mapping.source() + "\");\n");
            writer.write("\n");
        }
        writer.write(INDENT + "}\n");
        writer.write("\n");
    }

    public static void writeGeneratedAnnotation(Writer writer, ZonedDateTime dateTime) throws IOException {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        var timestamp = formatter.format(dateTime);
        writer.write("@Generated(\n");
        writer.write(INDENT + "value = \"" + MappingMetadataProcessor.class.getName() + "\",\n");
        writer.write(INDENT + "date = \"" + timestamp + "\"\n");
        writer.write(")\n");
    }

    public static void writeImports(Writer writer) throws IOException {
        writer.write("import javax.annotation.processing.Generated;\n");
        writer.write("import org.springframework.stereotype.Component;\n");
        writer.write("import java.util.Map;\n");
        writer.write("import java.util.HashMap;\n");
        writer.write("import io.github.savatware.mapstruct.extensions.spring.MappingMetadataLookup;\n");
        writer.write("\n");
    }

    public static void writeMappingMethods(Writer writer) throws IOException {
        writer.write(INDENT + "@Override\n");
        writer.write(INDENT + "public String getTargetMapping(String sourceName) {\n");
        writer.write(INDENT + INDENT + "return mapSourceToTarget.getOrDefault(sourceName, sourceName);\n");
        writer.write(INDENT + "}\n");
        writer.write("\n");
        writer.write(INDENT + "@Override\n");
        writer.write(INDENT + "public String getSourceMapping(String targetName) {\n");
        writer.write(INDENT + INDENT + "return mapTargetToSource.getOrDefault(targetName, targetName);\n");
        writer.write(INDENT + "}\n");
    }

}
