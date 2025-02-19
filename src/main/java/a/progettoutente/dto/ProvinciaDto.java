package a.progettoutente.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProvinciaDto {

    private Long id;

    private Integer codiceRegione;

    private String siglaProvincia;

    private String denominazioneProvincia;

    private String tipologiaProvincia;

    private Integer numeroComuni;

    private BigDecimal superficieKmq;

    private Integer codiceSovracomunale;
}
