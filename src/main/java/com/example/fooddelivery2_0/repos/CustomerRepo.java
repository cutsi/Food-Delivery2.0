package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.Customer;
import com.example.fooddelivery2_0.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    @Query(nativeQuery = true,
            value = "select * from customers where created_at between ?1 and ?2")
    List<Customer> findCustomersBetweenTwoDates(LocalDateTime startDate, LocalDateTime endDate);

}
