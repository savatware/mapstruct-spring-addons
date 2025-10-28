// no package declaration, hence default package

import io.github.savatware.mapstruct.addons.spring.MappingMetadata;
import org.mapstruct.Mapper;

@Mapper
public abstract class MyMapperWithoutPackage {

    @MappingMetadata("MyMappingWithoutPackageMetadata")
    public abstract MyDto toDto(MyEntity entity);

    public static record MyDto() {}
    public static record MyEntity() {}

}
