package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.FoodItem;
import com.example.fooddelivery2_0.entities.Restaurant;
import com.example.fooddelivery2_0.entities.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {
    List<FoodItem> findFoodItemsById(Long restaurantId);
    Optional<Restaurant> findByName(String name);
    List<Restaurant> findAllByOrderByIdAsc();
    Optional<Restaurant> findByOwners(RestaurantOwner owner);
}
