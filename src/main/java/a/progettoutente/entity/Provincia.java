package a.progettoutente.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Provincia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer codiceRegione;

    private String siglaProvincia;

    private String denominazioneProvincia;

    private String tipologiaProvincia;

    private Integer numeroComuni;

    @Column(precision = 12, scale = 4)
    private BigDecimal superficieKmq;

    private Integer codiceSovracomunale;

    public Provincia(String value, String value1, String value2, String value3, String value4, String value5, String value6) {
        this.codiceRegione = Integer.parseInt(value);
        this.siglaProvincia = value1;
        this.denominazioneProvincia = value2;
        this.tipologiaProvincia = value3;
        this.numeroComuni = Integer.parseInt(value4);
        this.superficieKmq = new BigDecimal(value5);
        this.codiceSovracomunale = Integer.parseInt(value6);
    }
}
