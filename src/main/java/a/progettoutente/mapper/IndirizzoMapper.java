package a.progettoutente.mapper;

import a.progettoutente.dto.IndirizzoDto;
import a.progettoutente.entity.Indirizzo;
import a.progettoutente.entity.Utente;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IndirizzoMapper {

    Indirizzo toEntity(IndirizzoDto indirizzoDto);

    @Mapping(target = "utente", ignore = true)
    IndirizzoDto toDto(Indirizzo indirizzo);

    List<Indirizzo> toEntityList(List<IndirizzoDto> dtoList);

    List<IndirizzoDto> toDtoList(List<Indirizzo> entityList);

    @AfterMapping
    default void setIndirizzi(@MappingTarget Utente utente) {
        if(utente.getIndirizzi() != null) {
            utente.getIndirizzi().forEach(indirizzo -> indirizzo.setUtente(utente));
        }
    }
}
