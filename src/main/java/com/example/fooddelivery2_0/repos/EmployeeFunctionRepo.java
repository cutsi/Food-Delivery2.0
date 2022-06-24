package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.EmployeeFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository //
public interface EmployeeFunctionRepo extends JpaRepository<EmployeeFunction, Long> {
    public Optional<EmployeeFunction> findByName(String name);
}
