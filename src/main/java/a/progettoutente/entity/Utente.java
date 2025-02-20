package a.progettoutente.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtente;
    private String nome;
    private String cognome;
    private String username;
    private String password;
    private String dataDiNascita;
    private boolean attivo;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Indirizzo> indirizzi;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_email",referencedColumnName = "idEmail")
    private Email email;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_telefono", referencedColumnName = "idTelefono")
    private Telefono telefono;

    @ManyToOne
    @JoinColumn(name = "fkRuolo", referencedColumnName = "idRuolo")
    private Ruolo ruolo;

}
