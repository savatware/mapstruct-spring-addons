package com.mycompany.test;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;
import io.github.savatware.mapstruct.extensions.spring.MappingMetadataLookup;

@Generated(
        value = "io.github.savatware.mapstruct.extensions.spring.processor.MappingMetadataProcessor",
        date = "2025-10-16T09:32:43+0200"
)
@Component
public class MyMappingMetadata implements MappingMetadataLookup {

    private final Map<String, String> mapSourceToTarget = new HashMap<>();
    private final Map<String, String> mapTargetToSource = new HashMap<>();

    public MyMappingMetadata() {
    }

    @Override
    public String getTargetMapping(String sourceName) {
        return mapSourceToTarget.getOrDefault(sourceName, sourceName);
    }

    @Override
    public String getSourceMapping(String targetName) {
        return mapTargetToSource.getOrDefault(targetName, targetName);
    }

}
