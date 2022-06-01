package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodItemRepo extends JpaRepository<FoodItem, Long> {
    Optional<FoodItem> findByName(String name);
    //Optional<FoodItem> findByPrice(String price);
}