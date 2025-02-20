package a.progettoutente.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "provincia")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Provincia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProvincia;

    @ManyToOne
    @JoinColumn(name = "fk_codice_regione", referencedColumnName = "codice_regione")
    private Regione regione;

    private String siglaProvincia;

    private String denominazioneProvincia;

    private String tipologiaProvincia;

    private Integer numeroComuni;

    @Column(precision = 12, scale = 4)
    private BigDecimal superficieKmq;

    @Column(name = "codice_sovracomunale", unique = true)
    private Integer codiceSovracomunale;

    @OneToMany(mappedBy = "provincia")
    private List<Comune> comuni;

    public Provincia(String value, String value1, String value2, String value3, String value4, String value5, String value6) {
        this.regione = new Regione(value);
        this.siglaProvincia = value1;
        this.denominazioneProvincia = value2;
        this.tipologiaProvincia = value3;
        this.numeroComuni = Integer.parseInt(value4);
        this.superficieKmq = new BigDecimal(value5);
        this.codiceSovracomunale = Integer.parseInt(value6);
    }

    @OneToOne(mappedBy = "provincia")
    private Indirizzo indirizzo;

    public Provincia(String value10) {
        this.codiceSovracomunale= Integer.parseInt(value10);
    }
}
