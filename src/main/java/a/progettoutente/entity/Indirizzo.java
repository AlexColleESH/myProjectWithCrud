package a.progettoutente.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Indirizzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIndirizzo;
    private String via;
    private Integer civico;
    @OneToOne
    @JoinColumn(name = "fk_cap", referencedColumnName = "idCap")
    private Cap cap;
    private String citta;
    @OneToOne
    @JoinColumn(name = "fk_provincia", referencedColumnName = "idProvincia")
    private Provincia provincia;

    @Enumerated(EnumType.STRING)
    private TipoIndirizzo tipoIndirizzo;

    @ManyToOne
    @JoinColumn(name = "fkUtente", referencedColumnName = "idUtente")
    private Utente utente;

    @Getter
    public enum TipoIndirizzo {
        RESIDENZA(1),
        FATTURAZIONE(2);

        private final int value;

        TipoIndirizzo(int value) {
            this.value = value;
        }
    }
}