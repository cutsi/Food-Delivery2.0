package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.Category;
import com.example.fooddelivery2_0.entities.Customer;
import com.example.fooddelivery2_0.entities.FoodItem;
import com.example.fooddelivery2_0.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FoodItemRepo extends JpaRepository<FoodItem, Long> {
    Optional<FoodItem> findByName(String name);
    List<FoodItem> findAllByRestaurant(Restaurant restaurant);
    //Optional<FoodItem> findByPrice(String price);
    List<FoodItem> findAllByCategory(Category category);

    List<FoodItem> findAllByName(String name);

    @Query(value = "SELECT * FROM food_items f WHERE f.name ILIKE CONCAT('%',:name,'%') or f.info ILIKE CONCAT('%',:name,'%')"
            , nativeQuery = true)
    List<FoodItem> findFoodItemsByNameSubstring(@Param("name") String name);

    @Query(value = "SELECT * FROM food_items f WHERE (f.name ILIKE CONCAT('%',:name,'%') or f.info ILIKE CONCAT('%',:name,'%'))" +
            "and f.restaurant_id =:restaurant "
            , nativeQuery = true)
    List<FoodItem> filterFoodItems(@Param("name") String name,Long restaurant);

    @Query(value = "select f.id from food_items as f " +
            "join restaurants as r on f.restaurant_id = r.id " +
            "join address as a on a.id = r.address_id " +
            "join city as c on a.city_id=c.id where c.name ILIKE CONCAT('%',:city,'%') " +
            "and (f.name ILIKE CONCAT('%',:name,'%') or f.info ILIKE CONCAT('%',:name,'%'))"
            , nativeQuery = true)
    List<Long> findFoodItemsByCity(@Param("name") String name,@Param("city") String city);

    @Query(value = "select * from food_items where restaurant_id=?1 order by id=?2 desc",
            nativeQuery = true)
    List<FoodItem> findFoodItemByRestaurantIdOrderByIdDesc(Long restaurantId, Long id);
}

