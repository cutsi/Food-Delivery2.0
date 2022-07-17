package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.CondimentName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CondimentNameRepo extends JpaRepository<CondimentName, Long> {
    CondimentName findByName(String name);
}
