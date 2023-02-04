package com.example.demoWeather.service;

import com.example.demoWeather.dto.city.CityEditDto;
import org.springframework.http.ResponseEntity;

public interface CityService {

    ResponseEntity<?> findAllCities();

    ResponseEntity<?> editCity(Long id, CityEditDto dto);

    ResponseEntity<?> updateWeather();

}
