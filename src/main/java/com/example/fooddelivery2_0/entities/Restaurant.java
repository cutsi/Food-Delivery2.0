package com.example.fooddelivery2_0.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String phone;
    private String image;
    private String name;
    private Double rating;
    private String banner;
    private String deliveryCost;
    @OneToOne
    private RestaurantAddress address;
    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantOwner> owners; //MAX 2 (1 owner and  1 employee created by the owner)
    @OneToMany(mappedBy = "restaurant")
    private List<Rating> ratings;
    @OneToMany(mappedBy = "restaurant")
    private List<Response> responses;
    @OneToMany(mappedBy = "restaurant")
    private List<FoodItem> foodItems;
    @OneToMany(mappedBy = "restaurant")
    private List<WorkingHours> workingHours; //MAX 7
    @OneToMany(mappedBy = "restaurant")
    private List<Order> orders;
    @JsonIgnore
    private String notificationReference;

    public Restaurant(String phone, String image, String name, String banner, String deliveryCost, RestaurantAddress address, List<FoodItem> foodItems) {
        this.phone = phone;
        this.image = image;
        this.name = name;
        this.banner = banner;
        this.deliveryCost = deliveryCost;
        this.address = address;
        this.foodItems = foodItems;
    }

}
