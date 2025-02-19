package a.progettoutente.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Integer cap;
    private String citta;
    private String provincia;

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
