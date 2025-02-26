package a.progettoutente.service;

import a.progettoutente.dto.ComuneDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ComuneService {

    ComuneDto getCitta (String citta);
}
