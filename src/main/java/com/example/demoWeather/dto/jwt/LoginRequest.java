package com.example.demoWeather.dto.jwt;

import lombok.Data;

@Data
public class LoginRequest {
	
	private String username;
	private String password;
	
}
