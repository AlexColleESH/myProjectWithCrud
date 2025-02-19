package a.progettoutente.controller;

import a.progettoutente.dto.UtenteDto;
import a.progettoutente.service.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PostMapping ("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UtenteDto utenteDto) {
        try {
            utenteService.creaUtente(utenteDto);
            return new ResponseEntity<>(utenteDto, HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>("Errore durante la creazione dell'utente: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/update-user")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<UtenteDto> updateUser(@RequestBody UtenteDto utenteDto) {
        utenteService.modificaUtente(utenteDto);
        return new ResponseEntity<>(utenteDto, HttpStatus.OK);
    }

    @GetMapping("/get-user/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>("Inserire id", HttpStatus.BAD_REQUEST);
        }
        UtenteDto utenteDto = utenteService.getUtenteById(id);
        return new ResponseEntity<>(utenteDto, HttpStatus.OK);
    }

    @GetMapping("/get-user-by-nome/{nome}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<?> getUserByNome(@PathVariable String nome) {
        List<UtenteDto> nomeUtenteDtoList = utenteService.getUtenteByNome(nome);
        if (nomeUtenteDtoList == null || nomeUtenteDtoList.isEmpty()) {
            return new ResponseEntity<>("nessun nome trovato", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(nomeUtenteDtoList, HttpStatus.OK);
    }

    @GetMapping("/get-user-by-cognome/{cognome}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<?> getUserByCognome(@PathVariable String cognome) {
        List<UtenteDto> cognomeUtenteDtoList = utenteService.getUtenteByCognome(cognome);
        if (cognomeUtenteDtoList == null || cognomeUtenteDtoList.isEmpty()) {
            return new ResponseEntity<>("nessun cognome trovato", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cognomeUtenteDtoList, HttpStatus.OK);
    }

    @GetMapping("/get-all-users")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<UtenteDto> utenteDtoList = utenteService.getAllUtenti();
        if (utenteDtoList == null || utenteDtoList.isEmpty()) {
            return new ResponseEntity<>("nessun utente trovato", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(utenteDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/delete-user/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        utenteService.eliminaUtente(id);
        return new ResponseEntity<>("Utente eliminato con successo", HttpStatus.OK);
    }
}
