package com.mycompany.test;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;
import io.github.savatware.mapstruct.addons.spring.MappingMetadataLookup;
import io.github.savatware.mapstruct.addons.spring.MappingMetadataLookup.MappingDescription;

@Generated(
        value = "io.github.savatware.mapstruct.addons.spring.processor.MappingMetadataProcessor",
        date = "2025-10-16T09:32:43+0200"
)
@Component
public class PersonMappingMetadata implements MappingMetadataLookup {

    private final List<MappingDescription> mappingDescriptions = new ArrayList<>();

    public PersonMappingMetadata() {
        mappingDescriptions.add(new MappingDescription("name", "fullName", "", "", "", "", "", false, new String[] {}, new String[] {}, "", new String[] {}, ""));
    }

    @Override
    public List<MappingDescription> getMappingDescriptions() {
        return mappingDescriptions;
    }

}
