package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.FoodItem;
import com.example.fooddelivery2_0.entities.Portion;
import com.example.fooddelivery2_0.entities.PortionName;
import com.example.fooddelivery2_0.repos.PortionNameRepo;
import com.example.fooddelivery2_0.repos.PortionRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class PortionService {
    private final PortionRepo portionRepo;
    private final PortionNameRepo portionNameRepo;

    public List<PortionName> getAllPortionNames(){
        return portionNameRepo.findAll();
    }
    public PortionName getPortionNameByName(String name){
        return portionNameRepo.findPortionNameByName(name);
    }

    public List<Portion> savePortions(String portionNamesStr, String pricesStr, FoodItem foodItem){
        var portionNames = Arrays.asList(portionNamesStr.split(","));
        var prices = Arrays.asList(pricesStr.split(","));
        List<Portion> portions = new ArrayList<>();
       for (int i = 0; i<prices.size();i++) {
           Portion portion = new Portion();
           portion.setFoodItem(foodItem);
           portion.setName(getPortionNameByName(portionNames.get(i)));
           portion.setPrice(prices.get(i));
           portion.setChecked(false);
           portionRepo.save(portion);
           portions.add(portion);
       }
       return portions;
    }

    public Portion getByNameIdAndFoodItem(Long nameId, Long foodItemId){
        return portionRepo.findByNameIdAndFoodItemId(nameId, foodItemId);
    }

    public void save(Portion portion) {
        portionRepo.save(portion);
    }
}
