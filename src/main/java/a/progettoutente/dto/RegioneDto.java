package a.progettoutente.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RegioneDto {

    private Long id;

    private String ripartizioneGeografica;

    private Integer codiceRegione;

    private String denominazioneRegione;

    private String tipologiaRegione;

    private Integer numeroProvince;

    private Integer numeroComuni;

    private BigDecimal superficieKmq;
}
