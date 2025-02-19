package a.progettoutente.service.impl;

import a.progettoutente.dto.EmailDto;
import a.progettoutente.dto.UtenteDto;
import a.progettoutente.entity.Telefono;
import a.progettoutente.entity.Utente;
import a.progettoutente.mapper.UtenteMapper;
import a.progettoutente.repository.UtenteRepository;
import a.progettoutente.service.EmailService;
import a.progettoutente.service.IndirizzoService;
import a.progettoutente.service.UtenteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UtenteServiceImpl implements UtenteService {

    private final UtenteRepository utenteRepository;
    private final IndirizzoService indirizzoService;
    private final UtenteMapper utenteMapper;
    private final EmailService emailService;

    public UtenteServiceImpl(UtenteRepository utenteRepository, IndirizzoService indirizzoService, UtenteMapper utenteMapper, EmailService emailService) {
        this.utenteRepository = utenteRepository;
        this.indirizzoService = indirizzoService;
        this.utenteMapper = utenteMapper;
        this.emailService = emailService;
    }

    @Override
    public void creaUtente(UtenteDto utenteDto) {
        EmailDto emailDto = emailService.getEmailByIndirizzoEmail(utenteDto.getEmail().getIndirizzoEmail());
        boolean exists = utenteRepository.existsByUsername(utenteDto.getUsername());
        if(emailDto == null && !exists) {
            utenteRepository.save(utenteMapper.toEntity(utenteDto));
        } else {
            throw new IllegalArgumentException("Lo username o l'indirizzo email sono già presenti");
        }
    }


    @Override
    public void modificaUtente(UtenteDto utenteDto) {
        if (utenteDto.getIdUtente() == null) {
            throw new IllegalArgumentException("Id utente non specificato");
        }
        Optional<Utente> utenteOptional = utenteRepository.findById(utenteDto.getIdUtente());
        if (utenteOptional.isPresent()) {
            Utente utente = utenteOptional.get();
            utente.setNome(utenteDto.getNome());
            utente.setCognome(utenteDto.getCognome());
            utente.setUsername(utenteDto.getUsername());
            utente.setPassword(utenteDto.getPassword());
            utente.setDataDiNascita(utenteDto.getDataDiNascita());
            utente.setAttivo(utenteDto.isAttivo());
            utenteRepository.save(utente);
        } else {
            throw new IllegalArgumentException("Utente non trovato");
        }
    }

    @Override
    public UtenteDto getUtenteById(Long id) {
        return utenteMapper.toDto(utenteRepository.findById(id).get());
    }

    @Override
    public List<UtenteDto> getUtenteByNome(String nome) {
        return utenteMapper.toDtoList(utenteRepository.findByNome(nome));
    }

    @Override
    public List<UtenteDto> getUtenteByCognome(String cognome) {
        return utenteMapper.toDtoList(utenteRepository.findByCognome(cognome));
    }

    @Override
    public UtenteDto findByUsername(String username) {
        return utenteMapper.toDto(utenteRepository.findByUsername(username));
    }


    @Override
    public void eliminaUtente(Long id) {
            if (id != null && utenteRepository.findById(id).isPresent()) {
                utenteRepository.deleteById(id);
            } else {
                throw new IllegalArgumentException("Utente non trovato o id nullo");
            }
    }

    @Override
    public List<UtenteDto> getAllUtenti() {
        return utenteMapper.toDtoList(utenteRepository.findAll());
    }


    @Override
    public void deleteTelefono(Telefono telefono) {
        Utente utente = utenteRepository.findByTelefono(telefono);
        utente.setTelefono(null);
        utenteRepository.save(utente);
    }
}
