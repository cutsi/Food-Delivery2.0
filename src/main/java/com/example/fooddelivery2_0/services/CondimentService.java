package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.Condiment;
import com.example.fooddelivery2_0.entities.FoodItem;
import com.example.fooddelivery2_0.entities.Restaurant;
import com.example.fooddelivery2_0.repos.CondimentNameRepo;
import com.example.fooddelivery2_0.repos.CondimentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CondimentService {
    private final CondimentRepo condimentRepo;
    private final RestaurantService restaurantService;
    private final FoodItemService foodItemService;
    private final CondimentNameRepo condimentNameRepo;
    public List<Condiment> getAllCondiments() {
        return condimentRepo.findAll();
    }

    public ArrayList<Object> getAllCondimentsByRestaurant(Restaurant restaurant){
        var condiments = new ArrayList<>();
        var condimentNames = new ArrayList<>();
        for (FoodItem foodItem:foodItemService.getAllByRestaurant(restaurant)) {
            for (Condiment condiment: foodItem.getCondiments()) {
                if(!condimentNames.contains(condiment.getName().getName())){
                    condiments.add(condiment);
                    condimentNames.add(condiment.getName().getName());
                }
            }
        }
        return condiments;

    }

    public void saveCondiments(String condimentNamesStr, String pricesStr, FoodItem foodItem){
        var condimentNames = Arrays.asList(condimentNamesStr.split(","));
        var prices = Arrays.asList(pricesStr.split(","));
        for (int i = 0; i<prices.size();i++) {
            Condiment condiment = new Condiment();
            condiment.setFoodItem(foodItem);
            condiment.setName(condimentNameRepo.findByName(condimentNames.get(i)));
            condiment.setPrice(prices.get(i));
            condimentRepo.save(condiment);
        }
    }}
