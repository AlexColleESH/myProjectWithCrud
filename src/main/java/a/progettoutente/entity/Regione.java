package a.progettoutente.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "regione")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Regione {

    private String ripartizioneGeografica;

    @Id
    @Column(name = "codice_regione")
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

    public Regione(String value) {
        this.codiceRegione = Integer.parseInt(value);
    }

    @OneToMany(mappedBy = "regione")
    private List<Provincia> province;

}
