package a.progettoutente.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RuoloDto {

    private Long idRuolo;
    private String ruolo;

    private List<UtenteDto> utenti;

}
