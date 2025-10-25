package io.github.savatware.mapstruct.extensions.spring.processor;


import io.github.savatware.mapstruct.extensions.spring.testutils.TestResourceReader;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.google.testing.compile.Compiler.javac;
import static io.github.savatware.mapstruct.extensions.spring.testutils.CompilationAssert.assertThat;

public class MappingMetadataProcessorTest {

    @Test
    void whenClassNameSpecified_thenFileName_isCorrect() {
        // given
        var mapperJavaFile = TestResourceReader.readJavaFileObject("MyMapper");
        var metadataProcessor = new MappingMetadataProcessor();
        var now = LocalDateTime.of(2025, 10, 16, 9, 32, 43, 123).atZone(ZoneId.of("Europe/Brussels"));
        metadataProcessor.setDateTime(now);
        var expectedSourceCode = TestResourceReader.readJavaSource("MyMappingMetadata");

        // when
        var compilation = javac()
                .withProcessors(metadataProcessor)
                .compile(List.of(mapperJavaFile));

        // then
        assertThat(compilation).succeeded();
        assertThat(compilation).generatedSourceCode("com.mycompany.test.MyMappingMetadata").isEqualTo(expectedSourceCode);
    }

}
