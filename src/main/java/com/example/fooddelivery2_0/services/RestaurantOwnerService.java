package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.RestaurantOwner;
import com.example.fooddelivery2_0.repos.RestaurantOwnerRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.SequenceGenerator;

@Service
@AllArgsConstructor
public class RestaurantOwnerService {
    private final RestaurantOwnerRepo restaurantOwnerRepo;

    public void save(RestaurantOwner restaurantOwner){
        restaurantOwnerRepo.save(restaurantOwner);
    }
}
