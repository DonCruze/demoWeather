package com.example.demoWeather.service;

import com.example.demoWeather.dto.UserEditDto;
import com.example.demoWeather.dto.jwt.SignupRequest;
import com.example.demoWeather.dto.city.CityAddDto;
import com.example.demoWeather.dto.jwt.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> login(LoginRequest loginRequest);

    ResponseEntity<?> register(SignupRequest signupRequest);

    ResponseEntity<?> subscribeToCity(CityAddDto dto, String token);

    ResponseEntity<?> findAllUsers();

    ResponseEntity<?> findOneUser(Long id);

    ResponseEntity<?> getSubscriptions(String token);

    ResponseEntity<?> editUser(Long id, UserEditDto dto);

}
