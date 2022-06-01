package com.example.fooddelivery2_0.entities;
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
    private String price; //will be fill with the check (default) portion
    @ManyToOne
    private Category category;
    @OneToMany
    private List<Portion> portions;
    @OneToMany
    private List<Condiment> condiments;

    public FoodItem(String image, String name, String info, Category category, List<Portion> portions, List<Condiment> condiments) {
        this.image = image;
        this.name = name;
        this.info = info;
        this.category = category;
        this.portions = portions;
        this.condiments = condiments;
    }

    public void setDefaultPrice(){
        if (portions!=null){
            portions.forEach(p->{
                if(p.isChecked())this.price=p.getPrice();
            });
        }
    }

}
