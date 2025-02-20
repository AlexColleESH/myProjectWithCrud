package a.progettoutente.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "nazione")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Nazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String siglaNazione;

    @Column(name = "codice_belfiore", unique = true)
    private String codiceBelfiore;

    private String denominazioneNazione;

    private String denominazioneCittadinanza;

    public Nazione(String value, String value1, String value2, String value3) {
        this.siglaNazione = value;
        this.codiceBelfiore = value1;
        this.denominazioneNazione = value2;
        this.denominazioneCittadinanza = value3;
    }

    public Nazione(String value6) {
        this.codiceBelfiore = value6;
    }
}
