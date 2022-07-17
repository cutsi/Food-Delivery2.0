package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.City;
import com.example.fooddelivery2_0.repos.CityRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CityService {
    private final CityRepo cityRepo;

    public Optional<City> getCityByName(String name){
        return cityRepo.findByName(name);
    }
    public List<City> getAllCities(){
        return cityRepo.findAll();
    }
    public List<String> getAllCityNames(){//FIX
        List<String> cityNames = new ArrayList<>();
        for (City city:cityRepo.findAll()) {
            cityNames.add(city.getName());
        }
        return cityNames;
    }

}
