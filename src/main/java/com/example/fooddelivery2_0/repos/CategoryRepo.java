package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.Category;
import com.example.fooddelivery2_0.entities.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Query(value = "select * from categories order by name=?1 desc", nativeQuery = true)
    List<Category> findCategoriesByNameOrderByNameDesc(String name);
    //select * from categories order by name='Hamburgeri' desc;

}
