package a.progettoutente.repository;

import a.progettoutente.entity.Telefono;
import a.progettoutente.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Utente findByTelefono(Telefono telefono);

    List<Utente> findByNome(String nome);

    List<Utente> findByCognome(String cognome);

    Utente findByUsername(String username);

    boolean existsByUsername(String username);
}
