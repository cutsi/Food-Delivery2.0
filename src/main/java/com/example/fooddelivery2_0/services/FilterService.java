package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.Category;
import com.example.fooddelivery2_0.entities.FoodItem;
import com.example.fooddelivery2_0.entities.Restaurant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FilterService {

    private final RestaurantService restaurantService;
    private final CategoryService categoryService;
    private final FoodItemService foodItemService;


    public List<Restaurant> filter(String filter){
        List<Restaurant> restaurantsByCategory = filterCategories(filter);
        List<Restaurant> restaurantsByFoodItem = filterFoodItems(filter);


        if(!restaurantsByFoodItem.isEmpty() && restaurantsByCategory.isEmpty()){
            return restaurantsByFoodItem;
        }
        if(restaurantsByFoodItem.isEmpty() && !restaurantsByCategory.isEmpty()){
            return restaurantsByCategory;
        }
        if(!restaurantsByFoodItem.isEmpty() && !restaurantsByCategory.isEmpty()){
            return getUniqueRestaurants(restaurantsByFoodItem,restaurantsByCategory);
        }
        return restaurantsByCategory;
    }
    private List<Restaurant> filterCategories(String category){
        List<Restaurant> restaurants = new ArrayList<>();
        if(!categoryService.getCategoryByName(category).isPresent()){
            return restaurants;
        }
        Category category1 = categoryService.getCategoryByName(category).get();
        List<FoodItem> allFoodItems = foodItemService.getAllByCategory(category1);
        for (FoodItem foodItem:allFoodItems) {
            if(!restaurants.contains(foodItem.getRestaurant())){
                restaurants.add(foodItem.getRestaurant());
            }
        }
        return restaurants;
    }
    private List<Restaurant> filterFoodItems(String foodItem){
        List<Restaurant> restaurants = new ArrayList<>();
        if(!foodItemService.getByName(foodItem).isPresent()){
            return restaurants;
        }
        List<FoodItem> foodItems = foodItemService.getAllByName(foodItem);
        for (FoodItem foodItem1: foodItems) {
            if(!restaurants.contains(foodItem1.getRestaurant())){
                restaurants.add(foodItem1.getRestaurant());
            }
        }
        return restaurants;
    }
    private List<Restaurant> getUniqueRestaurants(List<Restaurant> restaurants1, List<Restaurant> restaurants2){
        for (Restaurant restaurant: restaurants2) {
            if(!restaurants1.contains(restaurant)){
                restaurants1.add(restaurant);
            }
        }
        return restaurants1;
    }
}
