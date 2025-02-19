package a.progettoutente.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NazioneDto {

    private Long id;

    private String siglaNazione;

    private String codiceBelfiore;

    private String denominazioneNazione;

    private String denominazioneCittadinanza;
}
