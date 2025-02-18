package a.progettoutente.service;

import a.progettoutente.dto.EmailDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface EmailService {

    @Transactional
    void updateEmail(EmailDto emailDto);

    EmailDto getEmailById (Long id);

    EmailDto getEmailByIndirizzoEmail (String email);
    
    @Transactional
    void deleteEmail(Long id);

    List<EmailDto> getAllEmails();
}
