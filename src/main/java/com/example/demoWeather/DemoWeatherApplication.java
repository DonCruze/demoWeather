package com.example.demoWeather;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.demoWeather.client")
public class DemoWeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoWeatherApplication.class, args);
	}

}
