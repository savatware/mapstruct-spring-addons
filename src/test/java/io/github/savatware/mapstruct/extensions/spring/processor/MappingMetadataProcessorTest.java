package io.github.savatware.mapstruct.extensions.spring.processor;


import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

public class MappingMetadataProcessorTest {

    @Test
    void whenClassNameSpecified_thenFileName_isCorrect() {
        // given
        var mapperJavaFile = TestResourceReader.readJavaFileObject("MyMapper");
        var metadataProcessor = new MappingMetadataProcessor();

        // when
        var compilation = javac()
                .withProcessors(metadataProcessor)
                .compile(List.of(mapperJavaFile));

        // then
        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("com.mycompany.test.MyMappingMetadata")
                .contentsAsString(StandardCharsets.UTF_8)
                .isEqualTo(TestResourceReader.readJavaSource("MyMappingMetadata"));
    }

}
