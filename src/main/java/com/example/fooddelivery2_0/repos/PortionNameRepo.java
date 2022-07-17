package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.PortionName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortionNameRepo extends JpaRepository<PortionName, Long> {
    PortionName findPortionNameByName(String name);
}
