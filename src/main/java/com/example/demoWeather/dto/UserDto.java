package com.example.demoWeather.dto;

import com.example.demoWeather.models.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class UserDto {
    private String username;
    private String email;
    private Set<Role> roles;
}