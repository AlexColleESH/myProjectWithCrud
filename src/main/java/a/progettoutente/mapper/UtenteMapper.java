package a.progettoutente.mapper;

import a.progettoutente.dto.UtenteDto;
import a.progettoutente.entity.Utente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EmailMapper.class, IndirizzoMapper.class, TelefonoMapper.class, RuoloMapper.class,})
public abstract class UtenteMapper {

    @Autowired
    protected BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mapping(target = "password", expression = "java(bCryptPasswordEncoder.encode(utenteDto.getPassword()))")
    public abstract Utente toEntity(UtenteDto utenteDto);

    public abstract UtenteDto toDto(Utente utente);

    public abstract List<Utente> toEntityList(List<UtenteDto> dtoList);

    public abstract List<UtenteDto> toDtoList(List<Utente> entityList);
}
