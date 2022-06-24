package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.Address;
import com.example.fooddelivery2_0.repos.AddressRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepo addressRepo;

    public void save(Address address){
        addressRepo.save(address);
    }
}
