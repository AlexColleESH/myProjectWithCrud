package a.progettoutente.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "comune", uniqueConstraints = {@UniqueConstraint(columnNames = {"codice_istat"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comune {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String siglaProvincia;

    @Column(name = "codice_istat", unique = true)
    private String codiceIstat;

    private String denominazioneItaAltra;

    private String denominazioneIta;

    private String denominazioneAltra;

    private String flagCapoluogo;

    private String codiceBelfiore;

    @Column(precision = 10, scale = 7)
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private BigDecimal lat;

    @Column(precision = 10, scale = 7)
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private BigDecimal lon;

    @Column(precision = 12, scale = 4)
    private BigDecimal superficieKmq;

    @ManyToOne
    @JoinColumn(name = "fk_codice_sovracomunale", referencedColumnName = "codice_sovracomunale")
    private Provincia provincia;


    public Comune(String value, String value1, String value2, String value3, String value4, String value5, String value6, String value7, String value8, String value9, String value10) {
        this.siglaProvincia = value;
        this.codiceIstat = value1;
        this.denominazioneItaAltra = value2;
        this.denominazioneIta = value3;
        this.denominazioneAltra = value4;
        this.flagCapoluogo = value5;
        this.codiceBelfiore = value6;
        this.lat = new BigDecimal(value7);
        this.lon = new BigDecimal(value8);
        this.superficieKmq = new BigDecimal(value9);
        this.provincia = new Provincia(value10);
    }

    public static Comune fromCodiceIstat(String value) {
        Comune comune = new Comune();
        comune.setCodiceIstat(value);
        return comune;
    }

    @OneToMany(mappedBy = "comune")
    private List<Cap> capList;
}
