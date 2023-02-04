package com.example.demoWeather.dto.city;

import lombok.Data;

@Data
public class CityDto {
	private String title;
	private Boolean isRegionalCenter;
	private Object latitude;
	private Object longitude;
}