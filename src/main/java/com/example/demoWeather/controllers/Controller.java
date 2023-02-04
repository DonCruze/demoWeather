package com.example.demoWeather.controllers;


import com.example.demoWeather.dto.jwt.SignupRequest;
import com.example.demoWeather.service.CityService;
import com.example.demoWeather.dto.city.CityAddDto;
import com.example.demoWeather.dto.jwt.LoginRequest;
import com.example.demoWeather.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class Controller {

    private final UserService usersService;
    private final CityService cityService;


    public Controller(UserService usersService, CityService cityService) {
        this.usersService = usersService;
        this.cityService = cityService;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest requestDto) {
        return usersService.login(requestDto);
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody SignupRequest requestDto) {
        return usersService.register(requestDto);
    }

    @GetMapping("cities-list")
    public ResponseEntity<?> findAllCities() {
        return cityService.findAllCities();
    }

    @PostMapping("subscribe-to-city")
    public ResponseEntity<?> subscribeToCity(@RequestBody CityAddDto dto,
                                             @RequestHeader("Authorization") String token
                                             ) {
        return usersService.subscribeToCity(dto,token);
    }

    @GetMapping("get-subscriptions")
    public ResponseEntity<?> getSubscriptions(@RequestHeader("Authorization") String token) {
        return usersService.getSubscriptions(token);
    }

}
