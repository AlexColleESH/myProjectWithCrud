package a.progettoutente.controller;

import a.progettoutente.dto.TelefonoDto;
import a.progettoutente.service.TelefonoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/telephone")
public class TelefonoController {

    private final TelefonoService telefonoService;

    public TelefonoController(TelefonoService telefonoService) {
        this.telefonoService = telefonoService;
    }

    @PutMapping("/update-telephone")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
    ResponseEntity<TelefonoDto> updateTelephone (@RequestBody TelefonoDto telefonoDto) {
        telefonoService.updateTelephoneDto(telefonoDto);
        return new ResponseEntity<>(telefonoDto, HttpStatus.OK);
    }

    @GetMapping("/get-telephone/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN')")
    ResponseEntity<?> getTelefono (@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>("id non valido", HttpStatus.BAD_REQUEST);
        }
        TelefonoDto telefonoDto = telefonoService.findTelephoneById(id);
        return new ResponseEntity<>(telefonoDto, HttpStatus.OK);
    }

    @GetMapping("/get-telephone-by-telephone-number/{numeroTelefono}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN')")
    ResponseEntity<?> getTelefonoByNumeroTelefono (@PathVariable String numeroTelefono) {
        if (numeroTelefono == null) {
            return new ResponseEntity<>("numero telefono non valido", HttpStatus.BAD_REQUEST);
        }
        TelefonoDto telefonoDto = telefonoService.findTelephoneByTelephoneNumber(numeroTelefono);
        if (telefonoDto == null) {
            return new ResponseEntity<>("telefono non trovato", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(telefonoDto, HttpStatus.OK);
    }

    @GetMapping("/get-all-telephones")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    ResponseEntity<?> getAllTelefoni() {
        List<TelefonoDto> telefonoDtoList = telefonoService.findAllTelephones();
        if (telefonoDtoList == null || telefonoDtoList.isEmpty()) {
            return new ResponseEntity<>("nessun telefono presente", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(telefonoDtoList, HttpStatus.OK);
    }

    @PutMapping("/delete-telephone/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<?> deleteTelefono (@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>("id non valido", HttpStatus.BAD_REQUEST);
        }

        try {
            telefonoService.deleteTelefonoById(id);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("telefono eliminato con successo", HttpStatus.OK);
    }
}
