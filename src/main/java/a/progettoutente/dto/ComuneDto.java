package a.progettoutente.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ComuneDto {

    private Long id;

    private String siglaProvincia;

    private String codiceIstat;

    private String denominazioneItaAltra;

    private String denominazioneIta;

    private String denominazioneAltra;

    private String flagCapoluogo;

    private String codiceBelfiore;

    private BigDecimal lat;

    private BigDecimal lon;

    private BigDecimal superficieKmq;

    private Integer codiceSovracomunale;
}
