 package a.progettoutente.service;

import a.progettoutente.dto.IndirizzoDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional (readOnly = true)
public interface IndirizzoService {

    @Transactional
    void updateIndirizzo (IndirizzoDto indirizzoDto);

    IndirizzoDto getIndirizzoById (Long id);

    List<IndirizzoDto> getIndirizzoByVia (String via);

    @Transactional
    void deleteIndirizzoById (Long id);

    List<IndirizzoDto> getAllIndirizzi ();

}
