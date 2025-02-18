package a.progettoutente.service.impl;

import a.progettoutente.dto.TelefonoDto;
import a.progettoutente.entity.Telefono;
import a.progettoutente.mapper.TelefonoMapper;
import a.progettoutente.repository.TelefonoRepository;
import a.progettoutente.service.TelefonoService;
import a.progettoutente.service.UtenteService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class TelefonoServiceImpl implements TelefonoService {


    private final TelefonoRepository telefonoRepository;

    private final TelefonoMapper telefonoMapper;

    private final UtenteService utenteService;

    public TelefonoServiceImpl(TelefonoRepository telefonoRepository, TelefonoMapper telefonoMapper, UtenteService utenteService) {
        this.telefonoRepository = telefonoRepository;
        this.telefonoMapper = telefonoMapper;
        this.utenteService = utenteService;
    }

    @Override
    public void updateTelephoneDto (@RequestBody TelefonoDto telefonoDto) {
        if (telefonoDto.getIdTelefono() == null) {
            throw new IllegalArgumentException("Id telefono non valido");
        }else {
            Optional<Telefono> telefonoOptional = telefonoRepository.findById(telefonoDto.getIdTelefono());
            if (telefonoOptional.isPresent()) {
                Telefono telefono = telefonoOptional.get();
                telefono.setNumeroTelefono(telefonoDto.getNumeroTelefono());
                telefono.setTipoTelefono(telefonoDto.getTipoTelefono());
                telefonoRepository.save(telefono);
            }
        }
    }

    @Override
    public TelefonoDto findTelephoneById (Long id){
        return telefonoMapper.toDto(telefonoRepository.findById(id).get());
    }

    @Override
    public TelefonoDto findTelephoneByTelephoneNumber(String numeroTelefono) {
        return telefonoMapper.toDto(telefonoRepository.findByNumeroTelefono(numeroTelefono));
    }

    @Override
    public void deleteTelefonoById(Long idTelefono) {
        TelefonoDto telefonoDto = findTelephoneById(idTelefono);
        utenteService.deleteTelefono(telefonoMapper.toEntity(telefonoDto));
    }

    @Override
    public List<TelefonoDto> findAllTelephones() {
        return telefonoMapper.toDtoList(telefonoRepository.findAll());
    }
}
