package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.EmployeeFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //
public interface EmployeeFunctionRepo extends JpaRepository<EmployeeFunction, Long> {
}
