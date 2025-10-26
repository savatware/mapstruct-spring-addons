package io.github.savatware.mapstruct.addons.spring.processor;


import io.github.savatware.mapstruct.addons.spring.testutils.TestResourceReader;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.google.testing.compile.Compiler.javac;
import static io.github.savatware.mapstruct.addons.spring.testutils.CompilationAssert.assertThat;

class MappingMetadataProcessorTest {


    @Nested
    class ReadmeTests {

        // This test suite covers the code used in the README.md

        @Test
        void carExample() {
            // given
            var metadataProcessor = createMappingMetadataProcessor();
            var mapperJavaFile = TestResourceReader.readJavaFileObject("CarMapper");
            var expectedCarMetadataSourceCode = TestResourceReader.readJavaSource("CarMappingMetadata");
            var expectedPersonMetadataSourceCode = TestResourceReader.readJavaSource("PersonMappingMetadata");

            // when
            var compilation = javac()
                    .withProcessors(metadataProcessor)
                    .compile(List.of(mapperJavaFile));

            // then
            assertThat(compilation).succeeded();
            assertThat(compilation).generatedSourceJavaFiles().hasSize(2);
            assertThat(compilation).generatedSourceCode("com.mycompany.test.CarMappingMetadata").isEqualTo(expectedCarMetadataSourceCode);
            assertThat(compilation).generatedSourceCode("com.mycompany.test.PersonMappingMetadata").isEqualTo(expectedPersonMetadataSourceCode);
        }

    }

    // TODO test suite for packages: test with package, without package

    // TODO test suite for component type: test for spring for others

    // TODO test suite for checking Mapper's attributes are correctly put into MapperDescription

    @Nested
    class ClassNameTests {

        // TODO test with multiple source files

        // TODO test with multiple source files with same class name

        // TODO test when same classname used twice

        // TODO test when no specified classname

        @Test
        void whenClassNameSpecified_thenFileName_isCorrect() {
            // given
            var metadataProcessor = createMappingMetadataProcessor();
            var mapperJavaFile = TestResourceReader.readJavaFileObject("MyMapper");
            var expectedSourceCode = TestResourceReader.readJavaSource("MyMappingMetadata");

            // when
            var compilation = javac()
                    .withProcessors(metadataProcessor)
                    .compile(List.of(mapperJavaFile));

            // then
            assertThat(compilation).succeeded();
            assertThat(compilation).generatedSourceJavaFiles().hasSize(1);
            assertThat(compilation).generatedSourceCode("com.mycompany.test.MyMappingMetadata").isEqualTo(expectedSourceCode);
        }

        @Test
        void whenClassNameEmpty_thenGeneration_fails() {
            // given
            var metadataProcessor = createMappingMetadataProcessor();
            var mapperJavaFile = TestResourceReader.readJavaFileObject("MyMapperWithEmptyName");

            // when
            var compilation = javac()
                    .withProcessors(metadataProcessor)
                    .compile(List.of(mapperJavaFile));

            // then
            assertThat(compilation).failed();
            assertThat(compilation).hasError("Skipping creation for requested empty classname, defined in \"MyMapperWithEmptyName\"");
        }

        @Test
        void whenClassNameContainsDot_thenGeneration_fails() {
            // given
            var metadataProcessor = createMappingMetadataProcessor();
            var mapperJavaFile = TestResourceReader.readJavaFileObject("MyMapperWithDottedName");

            // when
            var compilation = javac()
                    .withProcessors(metadataProcessor)
                    .compile(List.of(mapperJavaFile));

            // then
            assertThat(compilation).failed();
            assertThat(compilation).hasError("lass name can not contain a dot, received: MyMapperWithDottedNameMetadata.java, defined in \"MyMapperWithDottedName\"");
        }

    }

    private MappingMetadataProcessor createMappingMetadataProcessor() {
        var metadataProcessor = new MappingMetadataProcessor();
        var now = LocalDateTime.of(2025, 10, 16, 9, 32, 43, 123).atZone(ZoneId.of("Europe/Brussels"));
        metadataProcessor.setDateTime(now);
        return metadataProcessor;
    }

}
