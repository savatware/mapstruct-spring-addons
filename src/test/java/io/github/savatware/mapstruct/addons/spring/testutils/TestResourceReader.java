package io.github.savatware.mapstruct.addons.spring.testutils;

import com.google.testing.compile.JavaFileObjects;

import javax.tools.JavaFileObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestResourceReader {

    public static String readResource(String filename) {
        try {
            var workingDir = Paths.get("src/test/resources");
            var content = Files.readString(workingDir.resolve(filename));
            return stripCarriageReturn(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String readJavaSource(String javaFilename) {
        return readResource("java-files/" + javaFilename + ".java");
    }

    public static JavaFileObject readJavaFileObject(String javaFilename) {
        var javaSourceCode = readJavaSource(javaFilename);
        return JavaFileObjects.forSourceString("com.mycompany.test." + javaFilename, javaSourceCode);
    }

    public static String stripCarriageReturn(String text) {
        return text.replace("\r", "");
    }

}
