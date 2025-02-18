package a.progettoutente.mapper;

import a.progettoutente.dto.TelefonoDto;
import a.progettoutente.entity.Telefono;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TelefonoMapper {

    @Mapping(target = "utente", ignore = true)
    Telefono toEntity(TelefonoDto telefonoDto);

    @Mapping(target = "utente", ignore = true)
    TelefonoDto toDto(Telefono telefono);

    List<Telefono> toEntityList(List<TelefonoDto> dtoList);

    List<TelefonoDto> toDtoList(List<Telefono> entityList);

}
