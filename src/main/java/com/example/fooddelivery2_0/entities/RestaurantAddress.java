package com.example.fooddelivery2_0.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
public class RestaurantAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    private String city;
    private String country;

    public RestaurantAddress(String address, String city, String country) {
        this.address = address;
        this.city = city;
        this.country = country;
    }
}