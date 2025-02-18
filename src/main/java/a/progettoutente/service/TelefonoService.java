package a.progettoutente.service;

import a.progettoutente.dto.TelefonoDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface TelefonoService {

    @Transactional
    void updateTelephoneDto(TelefonoDto telefonoDto);

    TelefonoDto findTelephoneById(Long id);

    TelefonoDto findTelephoneByTelephoneNumber(String numeroTelefono);

    @Transactional
    void deleteTelefonoById(Long id);

    List<TelefonoDto> findAllTelephones();
}
