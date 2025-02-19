package a.progettoutente.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Regione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ripartizioneGeografica;

    private Integer codiceRegione;

    private String denominazioneRegione;

    private String tipologiaRegione;

    private Integer numeroProvince;

    private Integer numeroComuni;

    @Column(precision = 12, scale = 4)
    private BigDecimal superficieKmq;

    public Regione(String value, String value1, String value2, String value3, String value4, String value5, String value6) {
        this.ripartizioneGeografica = value;
        this.codiceRegione = Integer.parseInt(value1);
        this.denominazioneRegione = value2;
        this.tipologiaRegione = value3;
        this.numeroProvince = Integer.parseInt(value4);
        this.numeroComuni = Integer.parseInt(value5);
        this.superficieKmq = new BigDecimal(value6);
    }
}
