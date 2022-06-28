package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.Portion;
import com.example.fooddelivery2_0.entities.PortionName;
import com.example.fooddelivery2_0.repos.PortionNameRepo;
import com.example.fooddelivery2_0.repos.PortionRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PortionService {
    private final PortionRepo portionRepo;
    private final PortionNameRepo portionNameRepo;

    public List<PortionName> getAllPortionNames(){
        return portionNameRepo.findAll();
    }
}
