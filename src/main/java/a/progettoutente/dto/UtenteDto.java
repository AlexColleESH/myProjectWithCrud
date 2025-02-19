package a.progettoutente.dto;

import lombok.Data;

import java.util.List;

@Data
public class UtenteDto {
    private Long idUtente;
    private String nome;
    private String cognome;
    private String username;
    private String password;
    private String dataDiNascita;
    private boolean attivo;

    public UtenteDto() {
        this.attivo= true;
    }

    private List<IndirizzoDto> indirizzi;
    private EmailDto email;
    private TelefonoDto telefono;
    private RuoloDto ruolo;
}
