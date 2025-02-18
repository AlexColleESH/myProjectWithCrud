package a.progettoutente.controller;

import a.progettoutente.dto.EmailDto;
import a.progettoutente.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PutMapping("/update-email")
    ResponseEntity<?> updateEmail (@RequestBody EmailDto emailDto){
        if (emailDto.getIdEmail() == null) {
            return new ResponseEntity<>("Id non valido", HttpStatus.BAD_REQUEST);
        }
        emailService.updateEmail(emailDto);
        return new ResponseEntity<>(emailDto, HttpStatus.OK);
    }

    @GetMapping("/get-email/{id}")
    public ResponseEntity<?> getEmail (@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>("Id non valido", HttpStatus.BAD_REQUEST);
        }
        EmailDto emailDto = emailService.getEmailById(id);
        return new ResponseEntity<>(emailDto, HttpStatus.OK);
    }

    @GetMapping("/get-email-by-indirizzo-email/{indirizzoEmail}")
    public ResponseEntity<?> getEmailByIndirizzoEmail (@PathVariable String indirizzoEmail) {
        if(indirizzoEmail == null) {
            return new ResponseEntity<>("Indirizzo email non valido", HttpStatus.BAD_REQUEST);
        }
        EmailDto emailDto = emailService.getEmailByIndirizzoEmail(indirizzoEmail);
        if (emailDto == null) {
            return new ResponseEntity<>("Nessun email trovato con questo indirizzo email", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(emailDto, HttpStatus.OK);
    }

    @GetMapping("/get-emails-list")
    public ResponseEntity<?> getEmailsList () {
        List<EmailDto> emailsDtoList = emailService.getAllEmails();
        if (emailsDtoList == null || emailsDtoList.isEmpty()) {
            return new ResponseEntity<>("Nessun email presente nel sistema", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(emailsDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/delete-email/{id}")
    public ResponseEntity<?> deleteEmail (@PathVariable Long id) {
        emailService.deleteEmail(id);
        return new ResponseEntity<>("Email eliminata con successo", HttpStatus.OK);
    }
}
