package a.progettoutente.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OpenWeatherItalianTranslationDto {

    private String nomeCitta;
    private String paese;
    private BigDecimal temperatura;
    private BigDecimal temperaturaPercepita;
    private BigDecimal temperaturaMinima;
    private BigDecimal temperaturaMassima;
    private int pressione;
    private int umidita;
    private String condizioniMeteo;
    private String descrizioneMeteo;
    private String iconaMeteo;
    private BigDecimal velocitaDelVento;
    private int direzioneDelVento;
}
