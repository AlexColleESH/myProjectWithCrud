package a.progettoutente.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenWeatherDto {
    private String cityName;
    private String country;
    private BigDecimal temperature;
    private BigDecimal feelsLike;
    private BigDecimal tempMin;
    private BigDecimal tempMax;
    private int pressure;
    private int humidity;
    private String weatherMain;
    private String weatherDescription;
    private String weatherIcon;
    private BigDecimal windSpeed;
    private int windDegree;
}
