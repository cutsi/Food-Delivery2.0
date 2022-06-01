package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.RestaurantAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //
public interface RestaurantAddressRepo extends JpaRepository<RestaurantAddress, Long> {
}
