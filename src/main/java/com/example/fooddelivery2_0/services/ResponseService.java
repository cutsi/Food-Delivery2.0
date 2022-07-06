package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.Response;
import com.example.fooddelivery2_0.entities.Restaurant;
import com.example.fooddelivery2_0.repos.ResponseRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ResponseService {
    private final ResponseRepo responseRepo;

    public void save(Response response){
        responseRepo.save(response);
    }

    public List<Response> getAllByRestaurant(Restaurant restaurant){
        return responseRepo.findAllByRestaurant(restaurant);
    }

    public Optional<Response> getById(Long id){
        return responseRepo.findById(id);
    }
}
