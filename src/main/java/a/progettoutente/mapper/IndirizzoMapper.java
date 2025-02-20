package a.progettoutente.mapper;

import a.progettoutente.dto.IndirizzoDto;
import a.progettoutente.entity.Cap;
import a.progettoutente.entity.Indirizzo;
import a.progettoutente.entity.Provincia;
import a.progettoutente.entity.Utente;
import a.progettoutente.repository.ProvinciaRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", uses = {CapMapper.class, ProvinciaMapper.class})
public abstract class IndirizzoMapper {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected ProvinciaRepository provinciaRepository;

    public abstract Indirizzo toEntity(IndirizzoDto indirizzoDto);

    @Mapping(target = "utente", ignore = true)
    public abstract IndirizzoDto toDto(Indirizzo indirizzo);

    public abstract List<Indirizzo> toEntityList(List<IndirizzoDto> dtoList);

    public abstract List<IndirizzoDto> toDtoList(List<Indirizzo> entityList);

    protected Map<String, Object> getIdCapAndIdProvincia(String citta) {
        String sql = """
        SELECT p.id_provincia
        FROM comune c
        JOIN provincia p ON c.fk_codice_sovracomunale = p.codice_sovracomunale
        WHERE c.denominazione_ita = ?
        GROUP BY p.id_provincia
        """;
        return jdbcTemplate.queryForMap(sql, citta);
    }


    @AfterMapping
    public void setIndirizzi(@MappingTarget Utente utente) {
        if(utente.getIndirizzi() != null) {
            utente.getIndirizzi().forEach(indirizzo -> indirizzo.setUtente(utente));
        }
    }


    @AfterMapping
    public void setForeignKeys(IndirizzoDto dto, @MappingTarget Indirizzo indirizzo) {
        Map<String, Object> result = getIdCapAndIdProvincia(dto.getCitta());
        if(result != null) {
            Number idProvinciaNumber = (Number) result.get("id_provincia");
            Long provinciaId = idProvinciaNumber.longValue();

            Provincia provincia = provinciaRepository.findById(provinciaId)
                    .orElseThrow(() -> new RuntimeException("Provincia non trovata con id: " + provinciaId));
            indirizzo.setProvincia(provincia);
        }
    }

}
