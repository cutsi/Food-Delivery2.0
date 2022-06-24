package com.example.fooddelivery2_0.entities;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String zip;

    public City(String name){
        this.name = name;
    }
}
