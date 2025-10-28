import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;
import io.github.savatware.mapstruct.addons.spring.MappingMetadataLookup;
import io.github.savatware.mapstruct.addons.spring.MappingDescription;

@Generated(
        value = "io.github.savatware.mapstruct.addons.spring.processor.MappingMetadataProcessor",
        date = "2025-10-16T09:32:43+0200"
)
@Component
public class MyMappingWithoutPackageMetadata implements MappingMetadataLookup {

    private final List<MappingDescription> mappingDescriptions = new ArrayList<>();

    public MyMappingWithoutPackageMetadata() {
    }

    @Override
    public List<MappingDescription> getMappingDescriptions() {
        return mappingDescriptions;
    }

}
