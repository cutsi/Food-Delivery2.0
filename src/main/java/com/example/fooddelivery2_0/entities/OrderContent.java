package com.example.fooddelivery2_0.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_contents")
public class OrderContent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String image;
    private String name;

    @JsonIgnore
    @Transient
    private String uniqueId;
    private int quantity;
    @ManyToOne
    private Portion portion;
    @ManyToMany()
    private List<Condiment> condiments;
    private String price;
}

