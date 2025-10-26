package io.github.savatware.mapstruct.addons.spring.testutils;

import com.google.testing.compile.Compilation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;

import javax.tools.JavaFileObject;

import java.util.stream.Collectors;

import static com.google.testing.compile.Compilation.Status.FAILURE;
import static com.google.testing.compile.Compilation.Status.SUCCESS;
import static java.util.Collections.emptyList;

public class CompilationAssert extends AbstractAssert<CompilationAssert, Compilation> {

    public CompilationAssert(Compilation actual) {
        super(actual, CompilationAssert.class);
    }

    public static CompilationAssert assertThat(Compilation actual) {
        return new CompilationAssert(actual);
    }

    public CompilationAssert succeeded() {
        isNotNull();
        if (actual.status().equals(FAILURE)) {
            failWithMessage("Expected compilation to have succeeded, but it failed");
        }
        return this;
    }

    public CompilationAssert failed() {
        isNotNull();
        if (actual.status().equals(SUCCESS)) {
            failWithMessage("Expected compilation to have failed, but it succeeded");
        }
        return this;
    }

    public SourceCodeAssert generatedSourceCode(String qualifiedName) {
        isNotNull();
        try {
            var generatedSourceCode = actual.generatedSourceFile(qualifiedName)
                    .orElseThrow()
                    .getCharContent(false)
                    .toString();
            return SourceCodeAssert.assertThat(generatedSourceCode);
        } catch (Exception e) {
            failWithMessage("Expected compilation to have generated source code, but it failed");
            return SourceCodeAssert.assertThat("");
        }
    }

    public ListAssert<JavaFileObject> generatedSourceJavaFiles() {
        isNotNull();
        if (actual.status().equals(FAILURE)) {
            return Assertions.assertThat(emptyList());
        }
        var generatedSourceFiles = actual.generatedFiles().stream()
                .filter(javaFileObject -> javaFileObject.toUri().toString().contains("SOURCE_OUTPUT"))
                .toList();
        return Assertions.assertThat(generatedSourceFiles);
    }

    public CompilationAssert hasError(String expectedError) {
        isNotNull();

        var isFound = actual.errors().stream()
                .anyMatch(error -> error.toString().contains(expectedError));
        if (!isFound) {
            var actualErrors = actual.errors().stream()
                    .map(error -> "  - " + error.toString())
                    .collect(Collectors.joining(System.lineSeparator()));
            failWithMessage("Expected error \"%s\" not found. \nActual errors: \n%s", expectedError, actualErrors);
        }

        return this;
    }

}
