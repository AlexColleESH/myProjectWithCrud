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
public class Telefono {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTelefono;
    private String numeroTelefono;

    @Enumerated(EnumType.STRING)
    private TipoTelefono tipoTelefono;

    @OneToOne(mappedBy = "telefono")
    private Utente utente;

    @Getter
    public enum TipoTelefono {
        FISSO(1),
        MOBILE(2);

        private final int value;

        TipoTelefono(int value) {
            this.value = value;
        }
    }

}
