package a.progettoutente.dto;

import a.progettoutente.entity.Indirizzo;
import a.progettoutente.entity.Utente;
import lombok.Data;

@Data
public class IndirizzoDto {
    private Long idIndirizzo;
    private String via;
    private Integer civico;
    private Integer cap;
    private String citta;
    private ProvinciaDto provincia;
    private Indirizzo.TipoIndirizzo tipoIndirizzo;
    private Utente utente;
}
