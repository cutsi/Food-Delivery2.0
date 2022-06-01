package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.OrderContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderContentRepo extends JpaRepository<OrderContent, Long> {
}
