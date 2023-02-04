package com.example.demoWeather.dto.city;

import lombok.Data;

@Data
public class RegionDto {
	private String datetime;
	private Double air_t;
	private String time_of_day;
	private Long datetime_ms;
	private String cloud_amount;
	private String weather_code;
	private CityDto city;
}