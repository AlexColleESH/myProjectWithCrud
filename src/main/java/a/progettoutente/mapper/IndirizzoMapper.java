package a.progettoutente.mapper;

import a.progettoutente.dto.IndirizzoDto;
import a.progettoutente.entity.Cap;
import a.progettoutente.entity.Indirizzo;
import a.progettoutente.entity.Provincia;
import a.progettoutente.entity.Utente;
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

    public abstract Indirizzo toEntity(IndirizzoDto indirizzoDto);

    @Mapping(target = "utente", ignore = true)
    public abstract IndirizzoDto toDto(Indirizzo indirizzo);

    public abstract List<Indirizzo> toEntityList(List<IndirizzoDto> dtoList);

    public abstract List<IndirizzoDto> toDtoList(List<Indirizzo> entityList);

    protected Map<String, Object> getIdCapAndIdProvincia(String citta) {
        String sql = """
        SELECT p.id_provincia, array_agg(ca.id_cap) as cap_ids
        FROM comune c
        JOIN provincia p ON c.fk_codice_sovracomunale = p.codice_sovracomunale
        JOIN cap ca ON c.codice_istat = ca.fk_codice_istat
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
            // Recupera gli ID come Number, così da gestire eventuali Integer/Long
            Number idCapNumber = (Number) result.get("id_cap");
            Number idProvinciaNumber = (Number) result.get("id_provincia");

            // Crea l'oggetto Cap e assegna l'id
            Cap cap = new Cap();
            cap.setIdCap(idCapNumber.longValue());
            indirizzo.setCap(cap);

            // Crea l'oggetto Provincia e assegna l'id
            Provincia provincia = new Provincia();
            provincia.setIdProvincia(idProvinciaNumber.longValue());
            indirizzo.setProvincia(provincia);
        }
    }

}
