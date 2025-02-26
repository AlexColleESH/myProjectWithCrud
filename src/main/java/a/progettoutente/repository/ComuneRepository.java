package a.progettoutente.repository;

import a.progettoutente.entity.Comune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComuneRepository extends JpaRepository<Comune, Long> {

    Comune findByDenominazioneIta(String citta);
}
