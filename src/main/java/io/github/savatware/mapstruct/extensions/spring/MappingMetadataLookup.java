package io.github.savatware.mapstruct.extensions.spring;

/**
 * Usage: the qualified SpringBean name is the name of the generated class but in camelCase.
 * e.g.
 *   @Autowired
 *   @Qualifier("myDtoMetadata")
 *   private final MappingMetadataLookup metadataLookup;
 */
public interface MappingMetadataLookup {

    /**
     * Get name of the target property based on the source property.
     * @param sourceName
     * @return name of target property
     */
    String getTargetMapping(String sourceName);

    /**
     * Get name of the source property based on the target property.
     * @param targetName
     * @return name of source property
     */
    String getSourceMapping(String targetName);

}
