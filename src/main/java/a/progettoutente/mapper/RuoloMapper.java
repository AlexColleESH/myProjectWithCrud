package a.progettoutente.mapper;

import a.progettoutente.dto.RuoloDto;
import a.progettoutente.entity.Ruolo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RuoloMapper {

    Ruolo toEntity(RuoloDto ruoloDto);

    @Mapping(target = "utenti", ignore = true)
    RuoloDto toDto(Ruolo ruolo);

    List<Ruolo> toEntityList(List<RuoloDto> ruoli);

    List<RuoloDto> toDtoList(List<Ruolo> ruoli);
}
