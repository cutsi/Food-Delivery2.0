package com.example.fooddelivery2_0.entities;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category { //DONE

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String image;
    @OneToMany(mappedBy = "category")
    private List<FoodItem> foodItems;

    public Category(String name, String image) {
        this.name = name;
        this.image = image;
    }

//    @OneToMany(mappedBy = "category")
//    private Set<FoodItem> foodItems = new HashSet<>();
//    public Category(String categoryName){
//        this.categoryName = categoryName;
//    }

}
