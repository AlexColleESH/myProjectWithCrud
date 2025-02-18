package a.progettoutente.mapper;

import a.progettoutente.dto.UtenteDto;
import a.progettoutente.entity.Utente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EmailMapper.class, IndirizzoMapper.class, TelefonoMapper.class})
public interface UtenteMapper {

    Utente toEntity(UtenteDto utenteDto);

    UtenteDto toDto(Utente utente);

    List<Utente> toEntityList(List<UtenteDto> dtoList);

    List<UtenteDto> toDtoList(List<Utente> entityList);
}
