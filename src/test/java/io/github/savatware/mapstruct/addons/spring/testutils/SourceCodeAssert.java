package io.github.savatware.mapstruct.addons.spring.testutils;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class SourceCodeAssert extends AbstractAssert<SourceCodeAssert, String> {

    public SourceCodeAssert(String actual) {
        super(actual, SourceCodeAssert.class);
    }

    public static SourceCodeAssert assertThat(String actual) {
        return new SourceCodeAssert(actual);
    }

    public SourceCodeAssert isEqualTo(String expectedSourceCode) {
        isNotNull();
        Assertions.assertThat(toSourceLines(actual))
                    .isEqualTo(toSourceLines(expectedSourceCode));
        return this;
    }

    private Object[] toSourceLines(String content) {
        return content.lines()
                .map(String::trim)
                .toArray();
    }

}
