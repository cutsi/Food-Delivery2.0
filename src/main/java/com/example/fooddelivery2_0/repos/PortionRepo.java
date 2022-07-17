package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.FoodItem;
import com.example.fooddelivery2_0.entities.Portion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PortionRepo extends JpaRepository<Portion, Long> {
    Portion findByNameAndAndFoodItem(String name, FoodItem foodItem);

    @Query(nativeQuery = true,
            value = "select * from portions where name_id=?1 and food_item_id=?2")
    Portion findByNameIdAndFoodItemId(Long nameId,Long foodItemId);
}
