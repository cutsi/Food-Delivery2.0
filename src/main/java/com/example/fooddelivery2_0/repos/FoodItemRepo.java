package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.Category;
import com.example.fooddelivery2_0.entities.FoodItem;
import com.example.fooddelivery2_0.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodItemRepo extends JpaRepository<FoodItem, Long> {
    Optional<FoodItem> findByName(String name);
    Optional<FoodItem> findByRestaurant(Restaurant restaurant);
    //Optional<FoodItem> findByPrice(String price);
    List<FoodItem> findAllByCategory(Category category);

    List<FoodItem> findAllByName(String name);
}