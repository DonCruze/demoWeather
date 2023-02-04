package com.example.demoWeather.dto.city;

import lombok.Data;

@Data
public class Region {
	private String datetime;
	private String air_t;
	private String time_of_day;
	private Long datetime_ms;
	private String cloud_amount;
	private String weather_code;
	private CityDto city;
}