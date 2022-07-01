package com.example.fooddelivery2_0.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food_items")
public class FoodItem { //DONE

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String image;
    private String name;
    private String info;
    private Boolean isHidden = false;

    @JsonBackReference(value = "category-foodItems")
    @ManyToOne
    private Category category;
    @JsonManagedReference(value="foodItem-portion")
    @OneToMany(mappedBy = "foodItem")
    private List<Portion> portions;
    @JsonManagedReference(value = "foodItem-condiments")
    @OneToMany(mappedBy = "foodItem")
    private List<Condiment> condiments;
    @ManyToOne
    private Restaurant restaurant;

    public FoodItem(String image, String name, String info, Category category, List<Portion> portions, List<Condiment> condiments) {
        this.image = image;
        this.name = name;
        this.info = info;
        this.category = category;
        this.portions = portions;
        this.condiments = condiments;
    }

    public List<Portion> getPortionsOrderById(){
        List<Portion> portionList = new ArrayList<>(portions);
        Collections.sort(portionList);
        return portionList;
    }
}
