package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.City;
import com.example.fooddelivery2_0.entities.CondimentName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepo extends JpaRepository<City, Long> {
    Optional<City> findByName(String name);

    @Override
    List<City> findAll();
}
