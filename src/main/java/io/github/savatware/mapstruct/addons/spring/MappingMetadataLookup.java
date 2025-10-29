package io.github.savatware.mapstruct.addons.spring;

import java.util.List;

/**
 * Implementations of this interface allow you to access the MapStruct mappings.
 *
 * Usage: the name of the qualified SpringBean is the name of the generated class but in camelCase.
 * e.g.
 *   @Autowired
 *   @Qualifier("myDtoMetadata")
 *   private final MappingMetadataLookup metadataLookup;
 */
public interface MappingMetadataLookup {

    List<MappingDescription> getMappingDescriptions();

    record MappingDescription(
        String source,
        String target,
        String dateFormat,
        String numberFormat,
        String constant,
        String expression,
        String defaultExpression,
        boolean ignore,
        String[] qualifiedByName,
        String[] conditionQualifiedByName,
        String conditionExpression,
        String[] dependsOn,
        String defaultValue
    ) { }
}
