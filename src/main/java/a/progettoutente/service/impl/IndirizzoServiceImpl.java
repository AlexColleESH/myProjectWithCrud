package a.progettoutente.service.impl;

import a.progettoutente.dto.IndirizzoDto;
import a.progettoutente.entity.Indirizzo;
import a.progettoutente.mapper.CapMapper;
import a.progettoutente.mapper.IndirizzoMapper;
import a.progettoutente.mapper.ProvinciaMapper;
import a.progettoutente.repository.IndirizzoRepository;
import a.progettoutente.service.IndirizzoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IndirizzoServiceImpl implements IndirizzoService {

    private final IndirizzoRepository indirizzoRepository;
    private final IndirizzoMapper indirizzoMapper;
    private final CapMapper capMapper;
    private final ProvinciaMapper provinciaMapper;

    public IndirizzoServiceImpl(IndirizzoRepository indirizzoRepository, IndirizzoMapper indirizzoMapper, CapMapper capMapper, ProvinciaMapper provinciaMapper) {
        this.indirizzoRepository = indirizzoRepository;
        this.indirizzoMapper = indirizzoMapper;
        this.capMapper = capMapper;
        this.provinciaMapper = provinciaMapper;
    }

    @Override
    public void updateIndirizzo (IndirizzoDto indirizzoDto) {
        if (indirizzoDto.getIdIndirizzo() == null) {
            throw new IllegalArgumentException("Id indirizzo non specificato");
        }
        Optional<Indirizzo> indirizzoOptional = indirizzoRepository.findById(indirizzoDto.getIdIndirizzo());
        if (indirizzoOptional.isPresent()) {
            Indirizzo indirizzo = indirizzoOptional.get();
            indirizzo.setVia(indirizzoDto.getVia());
            indirizzo.setCivico(indirizzoDto.getCivico());
            indirizzo.setCap(capMapper.toEntity(indirizzoDto.getCap()));
            indirizzo.setCitta(indirizzoDto.getCitta());
            indirizzo.setProvincia(provinciaMapper.toEntity(indirizzoDto.getProvincia()));
            indirizzo.setTipoIndirizzo(Indirizzo.TipoIndirizzo.RESIDENZA);
            indirizzoRepository.save(indirizzo);
        }
    }

    @Override
    public IndirizzoDto getIndirizzoById(Long id) {
        return indirizzoMapper.toDto (indirizzoRepository.findById(id).get());
    }

    @Override
    public List<IndirizzoDto> getIndirizzoByVia(String via) {
        return indirizzoMapper.toDtoList(indirizzoRepository.findByVia(via));
    }

    @Override
    public void deleteIndirizzoById(Long id) {
        if  (id != null && indirizzoRepository.findById(id).isPresent()) {
            indirizzoRepository.deleteById(id);
        }
        throw new IllegalArgumentException("Indirizzo non trovato");
    }

    @Override
    public List<IndirizzoDto> getAllIndirizzi() {
        return indirizzoMapper.toDtoList(indirizzoRepository.findAll());
    }

}
