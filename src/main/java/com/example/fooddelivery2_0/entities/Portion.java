package com.example.fooddelivery2_0.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "portions")
public class Portion implements Comparable<Portion>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String price;
    @Column(nullable = true)
    private boolean isChecked;
    @ManyToOne
    private PortionName name;
    @JsonBackReference(value="foodItem-portion")
    @ManyToOne
    private FoodItem foodItem;
    public Portion(String price, boolean isChecked, PortionName portionName) {
        this.price = price;
        this.isChecked = isChecked;
        this.name = portionName;
    }
    public Portion(String price, boolean isChecked, PortionName portionName, FoodItem foodItem) {
        this.price = price;
        this.isChecked = isChecked;
        this.name = portionName;
        this.foodItem = foodItem;
    }

    @Override
    public int compareTo(Portion portion) {
        return (int) (this.id - portion.getId());
    }
}
