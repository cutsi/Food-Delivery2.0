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
    private String price;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "foodItem")
    private List<Portion> portions;
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

    public void setDefaultPrice(){
        if (portions!=null){
            portions.forEach(p->{
                if(p.isChecked())this.price=p.getPrice();
            });
        }
    }
    public List<Portion> getPortionsOrderById(){
        List<Portion> portionList = new ArrayList<>(portions);
        Collections.sort(portionList);
        return portionList;
    }
}
