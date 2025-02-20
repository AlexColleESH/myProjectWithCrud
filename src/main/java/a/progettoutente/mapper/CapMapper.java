package a.progettoutente.mapper;

import a.progettoutente.dto.CapDto;
import a.progettoutente.entity.Cap;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CapMapper {

    Cap toEntity(CapDto capDto);

    CapDto toDto(Cap cap);

    List<Cap> toEntityList(List<CapDto> dtoList);

    List<CapDto> toDtoList(List<Cap> entityList);
}
