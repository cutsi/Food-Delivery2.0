package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.Customer;
import com.example.fooddelivery2_0.repos.CustomerRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;
    public void save(Customer customer){
        customerRepo.save(customer);
    }
}
