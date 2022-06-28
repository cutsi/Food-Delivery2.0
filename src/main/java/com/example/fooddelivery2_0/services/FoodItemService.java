package com.example.fooddelivery2_0.services;
import com.example.fooddelivery2_0.entities.Category;
import com.example.fooddelivery2_0.entities.FoodItem;
import com.example.fooddelivery2_0.entities.Restaurant;
import com.example.fooddelivery2_0.repos.FoodItemRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FoodItemService {
    private final FoodItemRepo foodItemRepo;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public List<FoodItem> getAllFoodItems(){
        return foodItemRepo.findAll();
    }
    public Optional<FoodItem> getById(Long id){
        return foodItemRepo.findById(id);
    }
    public List<FoodItem> getAllByRestaurant(Restaurant restaurant){
        return foodItemRepo.findAllByRestaurant(restaurant);
    }
    public Optional<FoodItem> getByName(String name){
        return foodItemRepo.findByName(name);
    }
    public List<FoodItem> getAllByCategory(Category category){
        return foodItemRepo.findAllByCategory(category);
    }
    public List<FoodItem> getAllByName(String name){
        return foodItemRepo.findAllByName(name);
    }

}

