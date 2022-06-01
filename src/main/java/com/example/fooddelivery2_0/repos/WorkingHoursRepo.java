package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.Restaurant;
import com.example.fooddelivery2_0.entities.WorkingHours;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkingHoursRepo {
    Optional<WorkingHours> findByClosesAt(String closesAt);
    Optional<WorkingHours> findByDayOfWeek(String dayOfWeek);
    List<WorkingHours> findByRestaurantOrderById(Restaurant restaurant);
    Optional<WorkingHours> findById(Long id);
    Optional<WorkingHours> findByOpensAt(String opensAt);
    Optional<WorkingHours> findByDayOfWeekAndRestaurant(String dayOfWeek, Restaurant restaurant);
}
