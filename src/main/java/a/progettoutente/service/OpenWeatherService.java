package a.progettoutente.service;

import a.progettoutente.dto.OpenWeatherDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Service
public class OpenWeatherService {

    private final WebClient webClient;
    private final String apiKey;
    private final String units;
    private final String lang;

    public OpenWeatherService(WebClient.Builder webClientBuilder,
                              @Value("${openweather.api.url}") String apiUrl,
                              @Value("${openweather.api.key}") String apiKey,
                              @Value("${openweather.api.units}") String units,
                              @Value("${openweather.api.lang}") String lang) {
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
        this.apiKey = apiKey;
        this.units = units;
        this.lang = lang;
    }


    public OpenWeatherDto getWeatherByCity(String city) {
        try {
            String json = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("q", city)
                            .queryParam("appid", apiKey)
                            .queryParam("units", units)
                            .queryParam("lang", lang)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            String cityName = root.path("name").asText();
            String country = root.path("sys").path("country").asText();
            BigDecimal temperature = root.path("main").path("temp").decimalValue();
            BigDecimal feelsLike = root.path("main").path("feels_like").decimalValue();
            BigDecimal tempMin = root.path("main").path("temp_min").decimalValue();
            BigDecimal tempMax = root.path("main").path("temp_max").decimalValue();
            int pressure = root.path("main").path("pressure").asInt();
            int humidity = root.path("main").path("humidity").asInt();
            JsonNode weather = root.path("weather").get(0);
            String weatherMain = weather.path("main").asText();
            String weatherDescription = weather.path("description").asText();
            String weatherIcon = weather.path("icon").asText();
            BigDecimal windSpeed = root.path("wind").path("speed").decimalValue();
            int windDegree = root.path("wind").path("deg").asInt();

            return new OpenWeatherDto(cityName, country, temperature, feelsLike,
                    tempMin, tempMax, pressure, humidity,
                    weatherMain, weatherDescription, weatherIcon,
                    windSpeed, windDegree);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Errore nella mappatura del JSON", e);
        }
    }

}

