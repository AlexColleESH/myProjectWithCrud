package a.progettoutente.controller;

import a.progettoutente.dto.IndirizzoDto;
import a.progettoutente.service.IndirizzoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class IndirizzoController {

    @Autowired
    private IndirizzoService indirizzoService;

    @PutMapping("/update-address")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
    ResponseEntity<IndirizzoDto> updateAddress (@RequestBody IndirizzoDto indirizzoDto) {
        indirizzoService.updateIndirizzo(indirizzoDto);
        return new ResponseEntity<>(indirizzoDto, HttpStatus.OK);
    }

    @GetMapping("/get-address/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<?> getAddressById (@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>("Id non valido", HttpStatus.BAD_REQUEST);
        }
        IndirizzoDto indirizzoDto = indirizzoService.getIndirizzoById(id);
        return new ResponseEntity<>(indirizzoDto, HttpStatus.OK);
    }

    @GetMapping("/get-address-by-way/{via}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<?> getAddressByVia (@PathVariable String via) {
        List<IndirizzoDto> indirizzoDtoList = indirizzoService.getIndirizzoByVia(via);
        if (indirizzoDtoList == null || indirizzoDtoList.isEmpty()) {
            return new ResponseEntity<>("Nessun indirizzo presente per la via specificata", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(indirizzoDtoList, HttpStatus.OK);
    }

    @GetMapping("/get-all-addresses")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> getAllAddresses () {
        List<IndirizzoDto> indirizzoDtoList = indirizzoService.getAllIndirizzi();
        if (indirizzoDtoList == null || indirizzoDtoList.isEmpty()) {
            return new ResponseEntity<>("Nessun indirizzo presente", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(indirizzoDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/delete-address/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<?> deleteAddressById (@PathVariable Long id) {
        indirizzoService.deleteIndirizzoById(id);
        return new ResponseEntity<>("Indirizzo eliminato con successo", HttpStatus.OK);
    }
}
