package com.example.demoWeather.dto.jwt;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupRequest {
	
	private String username;
	private String email;
	private String password;
	
}
