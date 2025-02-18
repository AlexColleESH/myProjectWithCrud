package a.progettoutente.repository;

import a.progettoutente.entity.Telefono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefonoRepository extends JpaRepository<Telefono, Long> {
    Telefono findByNumeroTelefono(String numeroTelefono);
}
