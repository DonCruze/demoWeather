package com.example.demoWeather.controllers;

import com.example.demoWeather.dto.UserEditDto;
import com.example.demoWeather.dto.city.CityEditDto;
import com.example.demoWeather.service.CityService;
import com.example.demoWeather.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService usersService;
    private final CityService cityService;

    public AdminController(UserService usersService, CityService cityService) {
        this.usersService = usersService;
        this.cityService = cityService;
    }


    @GetMapping("user-list")
    public ResponseEntity<?> findAllUsers() {

        return usersService.findAllUsers();
    }

    @GetMapping("user-details")
    public ResponseEntity<?> findOneUser(
            @RequestParam Long id
    ) {
        return usersService.findOneUser(id);
    }

    @PostMapping("edit-user")
    public ResponseEntity<?> editUser(

            @RequestParam Long id,
            @RequestBody UserEditDto dto

    ) {
        return usersService.editUser(id,dto);
    }

    @GetMapping("cities-list")
    public ResponseEntity<?> findAllCities() {
        return cityService.findAllCities();

    }

    @PostMapping("edit-city")
    public ResponseEntity<?> editCity(
            @RequestParam Long id,
            @RequestBody CityEditDto dto
    ) {
        return cityService.editCity(id,dto);
    }

    @GetMapping("update-city-weather")
    public ResponseEntity<?> updateWeather()
    {
        return cityService.updateWeather();
    }

}
