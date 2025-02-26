package a.progettoutente.service.impl;

import a.progettoutente.dto.ComuneDto;
import a.progettoutente.mapper.ComuneMapper;
import a.progettoutente.repository.ComuneRepository;
import a.progettoutente.service.ComuneService;
import org.springframework.stereotype.Service;

@Service
public class ComuneServiceImpl implements ComuneService {
    private final ComuneRepository comuneRepository;
    private final ComuneMapper comuneMapper;

    public ComuneServiceImpl(ComuneRepository comuneRepository, ComuneMapper comuneMapper) {
        this.comuneRepository = comuneRepository;
        this.comuneMapper = comuneMapper;
    }

    @Override
    public ComuneDto getCitta(String citta) {
        if (citta == null) {
            return null;
        }
       return comuneMapper.toDto(comuneRepository.findByDenominazioneIta(citta));
    }
}
