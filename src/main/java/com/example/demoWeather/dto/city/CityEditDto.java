package com.example.demoWeather.dto.city;

import lombok.Data;


@Data
public class CityEditDto {

    private String name;
    private Double temperature;
    private Long dateTimeMs;
    private String cloudAmount;
    private String dataTimeOfDay;
}
