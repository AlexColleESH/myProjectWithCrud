package a.progettoutente.mapper;

import a.progettoutente.dto.ComuneDto;
import a.progettoutente.entity.Comune;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComuneMapper {

    ComuneDto toDto(Comune comune);

    Comune toEntity(ComuneDto comuneDto);
}
