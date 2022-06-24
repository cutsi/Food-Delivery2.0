package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.EmployeeFunction;
import com.example.fooddelivery2_0.repos.EmployeeFunctionRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeFunctionService {
    private final EmployeeFunctionRepo employeeFunctionRepo;

    public Optional<EmployeeFunction> getFunctionByName(String name){
        return employeeFunctionRepo.findByName(name);
    }
    public Optional<EmployeeFunction> getFunctionById(Long id){
        return employeeFunctionRepo.findById(id);
    }
    public void save(EmployeeFunction emp){
        employeeFunctionRepo.save(emp);
    }
}
