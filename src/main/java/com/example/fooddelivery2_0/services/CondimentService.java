package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.Condiment;
import com.example.fooddelivery2_0.repos.CondimentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class CondimentService {
    private final CondimentRepo condimentRepo;

    public List<Condiment> getAllCondiments() {
        return condimentRepo.findAll();
    }
}
