package a.progettoutente.repository;

import a.progettoutente.entity.Cap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapRepository extends JpaRepository<Cap,Long > {
}
