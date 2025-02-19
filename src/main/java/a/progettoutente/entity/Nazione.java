package a.progettoutente.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Nazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String siglaNazione;

    private String codiceBelfiore;

    private String denominazioneNazione;

    private String denominazioneCittadinanza;

    public Nazione(String value, String value1, String value2, String value3) {
        this.siglaNazione = value;
        this.codiceBelfiore = value1;
        this.denominazioneNazione = value2;
        this.denominazioneCittadinanza = value3;
    }
}
