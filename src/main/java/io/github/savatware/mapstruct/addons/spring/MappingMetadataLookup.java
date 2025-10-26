package io.github.savatware.mapstruct.addons.spring;

import java.util.List;

/**
 * Usage: the qualified SpringBean name is the name of the generated class but in camelCase.
 * e.g.
 *   @Autowired
 *   @Qualifier("myDtoMetadata")
 *   private final MappingMetadataLookup metadataLookup;
 */
public interface MappingMetadataLookup {

    List<MappingDescription> getMappingDescriptions();

}
