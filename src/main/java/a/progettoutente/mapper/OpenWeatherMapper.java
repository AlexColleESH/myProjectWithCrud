package a.progettoutente.mapper;

import a.progettoutente.dto.OpenWeatherDto;
import a.progettoutente.dto.OpenWeatherItalianTranslationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OpenWeatherMapper {

    @Mapping(target = "nomeCitta", source = "cityName")
    @Mapping(target = "paese", source = "country")
    @Mapping(target = "temperatura", source = "temperature")
    @Mapping(target = "temperaturaPercepita", source = "feelsLike")
    @Mapping(target = "temperaturaMinima", source= "tempMin")
    @Mapping(target = "temperaturaMassima", source = "tempMax")
    @Mapping(target = "pressione", source = "pressure")
    @Mapping(target = "umidita", source = "humidity")
    @Mapping(target = "condizioniMeteo", source = "weatherMain")
    @Mapping(target = "descrizioneMeteo", source = "weatherDescription")
    @Mapping(target = "iconaMeteo", source = "weatherIcon")
    @Mapping(target = "velocitaDelVento", source = "windSpeed")
    @Mapping(target = "direzioneDelVento", source = "windDegree")
    OpenWeatherItalianTranslationDto toItalianOpenWeather (OpenWeatherDto openWeatherDto);
}
