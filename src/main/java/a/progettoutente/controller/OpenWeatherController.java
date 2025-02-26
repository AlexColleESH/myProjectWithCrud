package a.progettoutente.controller;

import a.progettoutente.dto.OpenWeatherDto;
import a.progettoutente.dto.OpenWeatherItalianTranslationDto;
import a.progettoutente.mapper.OpenWeatherMapper;
import a.progettoutente.service.OpenWeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenWeatherController {

    private final OpenWeatherService openWeatherService;
    private final OpenWeatherMapper openWeatherMapper;

    public OpenWeatherController(OpenWeatherService openWeatherService, OpenWeatherMapper openWeatherMapper) {
        this.openWeatherService = openWeatherService;
        this.openWeatherMapper = openWeatherMapper;
    }


    @GetMapping("/weather")
    public ResponseEntity<?> getWeather(@RequestParam String citta) {
        try {
            if (citta == null || citta.isEmpty()) {
                throw new IllegalArgumentException("Nessun indirizzo trovato per la città: " + citta);
            }
            OpenWeatherDto weather = openWeatherService.getWeatherByCity(citta);
            OpenWeatherItalianTranslationDto weatherTranslation = openWeatherMapper.toItalianOpenWeather(weather);
            return ResponseEntity.ok(weatherTranslation);
        } catch (Exception e) {
            return new ResponseEntity<>("città non presente nel database", HttpStatus.BAD_REQUEST);
        }
    }

}

