package a.progettoutente.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cap")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCap;

    @ManyToOne
    @JoinColumn(name = "fk_codice_istat", referencedColumnName = "codice_istat")
    private Comune comune;

    private String cap;

    public Cap(String value, String value1) {
        this.comune = Comune.fromCodiceIstat(value);
        this.cap = value1;
    }

}
