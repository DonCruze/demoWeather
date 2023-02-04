package com.example.demoWeather.client;

import com.example.demoWeather.dto.city.RegionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "weather",
        url = "https://meteoapi.meteo.uz/api/weather/current"
)
public interface GetWeather {

    @GetMapping()
    List<RegionDto> getCities();
}

