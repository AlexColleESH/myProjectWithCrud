package a.progettoutente.repository;

import a.progettoutente.entity.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndirizzoRepository extends JpaRepository<Indirizzo, Long> {
    List<Indirizzo> findByVia(String via);

    List<Indirizzo> findByCitta(String citta);
}
