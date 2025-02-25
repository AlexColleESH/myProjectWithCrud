package a.progettoutente.controller;

import a.progettoutente.dto.IndirizzoDto;
import a.progettoutente.dto.OpenWeatherDto;
import a.progettoutente.service.IndirizzoService;
import a.progettoutente.service.OpenWeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OpenWeatherController {

    private final OpenWeatherService openWeatherService;
    private final IndirizzoService indirizzoService;

    public OpenWeatherController(OpenWeatherService openWeatherService, IndirizzoService indirizzoService) {
        this.openWeatherService = openWeatherService;
        this.indirizzoService = indirizzoService;
    }


    @GetMapping("/weather")
    public ResponseEntity<OpenWeatherDto> getWeather(@RequestParam String citta) {
        try {
            List<IndirizzoDto> indirizzi = indirizzoService.getCitta(citta);
            if (indirizzi.isEmpty()) {
                throw new IllegalArgumentException("Nessun indirizzo trovato per la città: " + citta);
            }

            OpenWeatherDto weather = openWeatherService.getWeatherByCity(citta);
            return ResponseEntity.ok(weather);
        } catch (Exception e) {
            OpenWeatherDto errorDto = new OpenWeatherDto();
            errorDto.setCityName("Errore");
            errorDto.setWeatherDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
        }
    }

}

