package a.progettoutente.mapper;

import a.progettoutente.dto.EmailDto;
import a.progettoutente.entity.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    @Mapping(target = "utente", ignore = true)
    @Mapping(expression = "java(null == emailDto.getIndirizzoEmail() ? \"\" : emailDto.getIndirizzoEmail().toLowerCase())", target = "indirizzoEmail")
    Email toEntity(EmailDto emailDto);

    @Mapping(target = "utente", ignore = true)
    EmailDto toDto(Email email);

    List<Email> toEntityList(List<EmailDto> dtoList);

    List<EmailDto> toDtoList(List<Email> entityList);

}
