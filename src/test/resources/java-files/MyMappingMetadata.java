package com.mycompany.test;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;
import io.github.savatware.mapstruct.extensions.spring.MappingMetadataLookup;
import io.github.savatware.mapstruct.extensions.spring.MappingDescription;

@Generated(
        value = "io.github.savatware.mapstruct.extensions.spring.processor.MappingMetadataProcessor",
        date = "2025-10-16T09:32:43+0200"
)
@Component
public class MyMappingMetadata implements MappingMetadataLookup {

    private final List<MappingDescription> mappingDescriptions = new ArrayList<>();

    public MyMappingMetadata() {
    }

    @Override
    public List<MappingDescription> getMappingDescriptions() {
        return mappingDescriptions;
    }

}
