package com.example.fooddelivery2_0.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "condiments")
public class Condiment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String price;
    @ManyToOne
    private CondimentName name;
    @JsonBackReference(value = "foodItem-condiments")
    @ManyToOne
    private FoodItem foodItem;
    //@ManyToMany(mappedBy = "condiments")
    //@Transient
    //@JsonIgnore
    //private String displayName;

    public Condiment(String price, CondimentName condimentName) {
        this.price = price;
        this.name = condimentName;
    }

    public Condiment(String price, CondimentName condimentName, FoodItem foodItem) {
        this.price = price;
        this.name = condimentName;
        this.foodItem = foodItem;
    }

}
