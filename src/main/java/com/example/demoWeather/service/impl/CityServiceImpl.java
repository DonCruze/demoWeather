package com.example.demoWeather.service.impl;


import com.example.demoWeather.service.CityService;
import com.example.demoWeather.client.GetWeather;
import com.example.demoWeather.dto.city.CityEditDto;
import com.example.demoWeather.dto.city.RegionDto;
import com.example.demoWeather.models.City;
import com.example.demoWeather.repository.CityRepository;
import com.example.demoWeather.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private final GetWeather getWeather;
    private final UserRepository usersRepository;
    private final CityRepository cityRepository;

    public CityServiceImpl(GetWeather getWeather, UserRepository usersRepository, CityRepository cityRepository) {
        this.getWeather = getWeather;
        this.usersRepository = usersRepository;
        this.cityRepository = cityRepository;
    }


    @Override
    public ResponseEntity<?> findAllCities() {

        List<City> response = cityRepository.findAllByDeletedFalse();

        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<?> editCity(Long id, CityEditDto dto) {

        Optional<City> oneCity = cityRepository.findById(id);

        if(oneCity.isPresent()){
            City cityModel = oneCity.get();

            cityModel.setName(dto.getName());
            cityModel.setTemperature(dto.getTemperature());
            cityModel.setCloudAmount(dto.getCloudAmount());
            cityModel.setDateTimeMs(dto.getDateTimeMs());
            cityModel.setDataTimeOfDay(dto.getDataTimeOfDay());

            cityRepository.save(cityModel);

            return new ResponseEntity("changes save", HttpStatus.OK);

        }

        return new ResponseEntity("id not found", HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<?> updateWeather() {

        List<RegionDto> response = getWeather.getCities();

        response.forEach(data ->{

            Optional<City> oneCity = cityRepository.findByName(data.getCity().getTitle());
            City info = oneCity.get();

            info.setName(data.getCity().getTitle());
            info.setTemperature(data.getAir_t());
            info.setDateTimeMs(data.getDatetime_ms());
            info.setCloudAmount(data.getCloud_amount());
            info.setDataTimeOfDay(data.getTime_of_day());

            cityRepository.save(info);
        });

        return new ResponseEntity("weather data changed", HttpStatus.OK);
    }

    @PostConstruct
    public String getCities(){

        List<RegionDto> response = getWeather.getCities();

        List<City> cities = cityRepository.findAll();

        if(cities.isEmpty()) {

            response.forEach(data -> {
                System.out.println(data);
                cityRepository.save(City.builder()
                        .name(data.getCity().getTitle())
                        .temperature(data.getAir_t())
                        .dateTimeMs(data.getDatetime_ms())
                        .dataTimeOfDay(data.getTime_of_day())
                        .cloudAmount(data.getCloud_amount())
                        .deleted(false)
                        .build()
                );
            });
        } else {

            response.forEach(data ->{

                Optional<City> oneCity = cityRepository.findByName(data.getCity().getTitle());
                City info = oneCity.get();

                info.setName(data.getCity().getTitle());
                info.setTemperature(data.getAir_t());
                info.setDateTimeMs(data.getDatetime_ms());
                info.setCloudAmount(data.getCloud_amount());
                info.setDataTimeOfDay(data.getTime_of_day());

                cityRepository.save(info);
            });
        }

        return "saved";
    }

    @Scheduled(fixedDelay = 60*60*1000)
    public void timer(){
        changeData();
    }

    public void changeData(){
        List<RegionDto> response = getWeather.getCities();
        response.forEach(data ->{

            Optional<City> oneCity = cityRepository.findByName(data.getCity().getTitle());
            City info = oneCity.get();

            info.setName(data.getCity().getTitle());
            info.setTemperature(data.getAir_t());
            info.setDateTimeMs(data.getDatetime_ms());
            info.setCloudAmount(data.getCloud_amount());
            info.setDataTimeOfDay(data.getTime_of_day());

            cityRepository.save(info);
        });
    }



}
