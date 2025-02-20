package a.progettoutente.mapper;

import a.progettoutente.dto.ProvinciaDto;
import a.progettoutente.entity.Provincia;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProvinciaMapper {

    Provincia toEntity(ProvinciaDto provinciaDto);

    ProvinciaDto toDto(Provincia provincia);

    List<Provincia> toEntityList(List<ProvinciaDto> dtoList);

    List<ProvinciaDto> toDtoList(List<Provincia> entityList);

}
