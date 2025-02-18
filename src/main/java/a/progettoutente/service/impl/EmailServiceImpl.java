package a.progettoutente.service.impl;

import a.progettoutente.dto.EmailDto;
import a.progettoutente.entity.Email;
import a.progettoutente.mapper.EmailMapper;
import a.progettoutente.repository.EmailRepository;
import a.progettoutente.service.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final EmailMapper emailMapper;

    public EmailServiceImpl(EmailRepository emailRepository, EmailMapper emailMapper) {
        this.emailRepository = emailRepository;
        this.emailMapper = emailMapper;
    }

    @Override
    public void updateEmail (EmailDto emailDto) {
        Optional<Email> emailOptional = emailRepository.findById(emailDto.getIdEmail());
        if (emailOptional.isPresent()) {
            Email email = emailOptional.get();
            email.setIdEmail(emailDto.getIdEmail());
            email.setIndirizzoEmail(emailDto.getIndirizzoEmail());
            emailRepository.save(email);
        }
    }

    @Override
    public EmailDto getEmailById(Long id) {
        return emailMapper.toDto(emailRepository.findById(id).get());
    }

    @Override
    public EmailDto getEmailByIndirizzoEmail(String indirizzoEmail) {
        return emailMapper.toDto(emailRepository.findByIndirizzoEmail(indirizzoEmail.toLowerCase()));
    }

    @Override
    public List<EmailDto> getAllEmails() {
        return emailMapper.toDtoList(emailRepository.findAll());
    }

    @Override
    public void deleteEmail(Long id) {
        if (id != null && emailRepository.findById(id).isPresent()){
            emailRepository.deleteById(id);
        }
        else {
            throw new IllegalArgumentException("Id email non valido");
        }
    }
}
