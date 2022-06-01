package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
