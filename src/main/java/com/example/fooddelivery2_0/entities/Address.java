package com.example.fooddelivery2_0.entities;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToOne
    private City city;
    private String country;

    public Address(String name, City city, String country) {
        this.name = name;
        this.city = city;
        this.country = country;
    }
    public Address(String name, City city) {
        this.name = name;
        this.city = city;
    }

}