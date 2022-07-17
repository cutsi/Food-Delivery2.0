package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.Response;
import com.example.fooddelivery2_0.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseRepo extends JpaRepository<Response, Long> {

    List<Response> findAllByRestaurant(Restaurant restaurant);
}
