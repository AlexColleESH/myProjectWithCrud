package a.progettoutente.dto;

import a.progettoutente.entity.Telefono;
import a.progettoutente.entity.Utente;
import lombok.Data;

@Data
public class TelefonoDto {

    private Long idTelefono;
    private String numeroTelefono;
    private Telefono.TipoTelefono tipoTelefono;
    private Utente utente;
}
