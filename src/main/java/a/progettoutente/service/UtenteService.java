package a.progettoutente.service;

import a.progettoutente.dto.UtenteDto;
import a.progettoutente.entity.Telefono;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface UtenteService {

    @Transactional
    void creaUtente (UtenteDto utenteDto);

    @Transactional
    void modificaUtente(UtenteDto utenteDto);

    UtenteDto getUtenteById(Long id);

    @Transactional
    void eliminaUtente(Long id);

    List<UtenteDto> getAllUtenti();

    List<UtenteDto> getUtenteByNome(String nome);

    @Transactional
    void deleteTelefono(Telefono idTelefono);

    List<UtenteDto> getUtenteByCognome(String cognome);

    UtenteDto findByUsername(String username);
}
