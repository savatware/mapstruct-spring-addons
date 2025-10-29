package com.mycompany.test;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;
import io.github.savatware.mapstruct.addons.spring.MappingMetadataLookup;
import io.github.savatware.mapstruct.addons.spring.MappingMetadataLookup.MappingDescription;

@Generated(
    value = "io.github.savatware.mapstruct.addons.spring.processor.metadata.MappingMetadataProcessor",
    date = "2025-10-16T09:32:43+0200"
)
@Component
public class CarMappingMetadata implements MappingMetadataLookup {

    private final List<MappingDescription> mappingDescriptions;

    public CarMappingMetadata() {
        mappingDescriptions = new ArrayList<>();
        mappingDescriptions.add(new MappingDescription("make", "manufacturer", "", "", "", "", "", false, new String[] {}, new String[] {}, "", new String[] {}, ""));
        mappingDescriptions.add(new MappingDescription("numberOfSeats", "seatCount", "", "", "", "", "", false, new String[] {}, new String[] {}, "", new String[] {}, ""));
    }

    @Override
    public List<MappingDescription> getMappingDescriptions() {
        return mappingDescriptions;
    }

}
