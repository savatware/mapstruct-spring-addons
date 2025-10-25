package io.github.savatware.mapstruct.extensions.spring.testutils;

import com.google.testing.compile.Compilation;
import org.assertj.core.api.AbstractAssert;

import static com.google.testing.compile.Compilation.Status.FAILURE;

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
}
