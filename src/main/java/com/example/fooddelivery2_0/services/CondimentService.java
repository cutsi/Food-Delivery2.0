package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.Condiment;
import com.example.fooddelivery2_0.entities.FoodItem;
import com.example.fooddelivery2_0.entities.Restaurant;
import com.example.fooddelivery2_0.repos.CondimentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CondimentService {
    private final CondimentRepo condimentRepo;
    private final RestaurantService restaurantService;
    private final FoodItemService foodItemService;
    public List<Condiment> getAllCondiments() {
        return condimentRepo.findAll();
    }

    public List<Condiment> getAllCondimentsByRestaurant(Restaurant restaurant){
        List <Condiment> condiments = new ArrayList<>();
        List<String> condimentNames = new ArrayList<>();
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
}
