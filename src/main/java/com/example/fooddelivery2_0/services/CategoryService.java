package com.example.fooddelivery2_0.services;
import com.example.fooddelivery2_0.entities.Category;
import com.example.fooddelivery2_0.entities.FoodItem;
import com.example.fooddelivery2_0.entities.Restaurant;
import com.example.fooddelivery2_0.repos.CategoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;
    private final FoodItemService foodItemService;

    public Optional<Category> getCategoryByName(String categoryName){
        return categoryRepo.findByName(categoryName);
    }
    public Optional<Category> getCategoryById(Long id){
        return categoryRepo.findById(id);
    }
    public void save(Category category){
        categoryRepo.save(category);
    }
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }
    public List<Category> getAllOrderByName(String name){
        return categoryRepo.findCategoriesByNameOrderByNameDesc(name);
    }

}
