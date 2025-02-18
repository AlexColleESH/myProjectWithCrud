package a.progettoutente.dto;

import a.progettoutente.entity.Utente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {

    private Long idEmail;
    private String indirizzoEmail;
    private Utente utente;
}
