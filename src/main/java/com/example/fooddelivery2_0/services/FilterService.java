package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.FoodItem;
import com.example.fooddelivery2_0.entities.Restaurant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class FilterService {

    private final RestaurantService restaurantService;
    private final CategoryService categoryService;
    private final FoodItemService foodItemService;
    private final CityService cityService;
    private String capitalizeFirstLetter(String word){
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase(Locale.ROOT);
    }

    public ArrayList<Object> filter(String word, String city){
        var foodItems = new ArrayList<>();
        for (Long foodItemId:foodItemService.getFoodItemsByCity(word, city)) {
            foodItems.add(foodItemService.getById(foodItemId).get());
        }
        return foodItems;
    }
    private List<FoodItem> filterFoodItems(String foodItem){
        return foodItemService.getFoodItemsByName(foodItem);

    }
    private List<Restaurant> getUniqueRestaurants(List<Restaurant> restaurants1, List<Restaurant> restaurants2){
        for (var restaurant: restaurants2) {
            if(!restaurants1.contains(restaurant)){
                restaurants1.add(restaurant);
            }
        }
        return restaurants1;
    }
}
