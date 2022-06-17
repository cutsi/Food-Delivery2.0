package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.Category;
import com.example.fooddelivery2_0.entities.FoodItem;
import com.example.fooddelivery2_0.entities.Restaurant;
import com.example.fooddelivery2_0.entities.RestaurantOwner;
import com.example.fooddelivery2_0.repos.RestaurantRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepo restaurantRepo;

    public List<Restaurant> getAllRestaurants(){
        return restaurantRepo.findAllByOrderByIdAsc();
    }

    public List<FoodItem> getFoodItemsFromRestaurantById(Long id){
        return restaurantRepo.findFoodItemsById(id);
        //return new ArrayList<>();
    }

    public Optional<Restaurant> getRestaurantByName(String name){
        return restaurantRepo.findByName(name);
    }

    public Optional<Restaurant> getRestaurantById(Long id){
        return restaurantRepo.findById(id);
    }

    public List<Category> getCategoriesFromRestaurant(List<FoodItem> menu){
        List<Category> categories = new ArrayList<>();
        for (FoodItem foodItem: menu) {
            if(!categories.contains(foodItem.getCategory())){
                categories.add(foodItem.getCategory());
            }
        }
        return categories;
    }
    public Optional<Restaurant> getRestaurantByOwner(RestaurantOwner owner){
        return restaurantRepo.findByOwners(owner);
        //return Optional.empty();
    }

}

