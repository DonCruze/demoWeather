package com.example.demoWeather.models;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity(name="city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "dateTimeMs")
    private Long dateTimeMs;

    @Column(name = "cloudAmount")
    private String cloudAmount;

    @Column(name = "dataTimeOfDay")
    private String dataTimeOfDay;

    public City(Long id, String name, Double temperature, Boolean deleted, Long dateTimeMs, String cloudAmount, String dataTimeOfDay) {
        this.id = id;
        this.name = name;
        this.temperature = temperature;
        this.deleted = deleted;
        this.dateTimeMs = dateTimeMs;
        this.cloudAmount = cloudAmount;
        this.dataTimeOfDay = dataTimeOfDay;
    }
}
