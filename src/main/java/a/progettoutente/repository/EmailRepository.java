package a.progettoutente.repository;

import a.progettoutente.dto.EmailDto;
import a.progettoutente.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    Email findByIndirizzoEmail(String indirizzoEmail);
}
