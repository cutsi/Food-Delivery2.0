package com.example.fooddelivery2_0.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurants")
public class Restaurant implements Comparable<Restaurant>{

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
    private Address address;
    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantOwner> owners; //MAX 2 (1 owner and  1 employee created by the owner)
    @JsonManagedReference(value = "restaurant-ratings")
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

    public Restaurant(String phone, String image, String name, String banner, String deliveryCost, Address address, List<FoodItem> foodItems) {
        this.phone = phone;
        this.image = image;
        this.name = name;
        this.banner = banner;
        this.deliveryCost = deliveryCost;
        this.address = address;
        this.foodItems = foodItems;
    }
    public Restaurant(String phone, String name, String image, String banner){
        this.phone = phone;
        this.name = name;
        this.image=image;
        this.banner = banner;
    }
    public Restaurant(String phone, String name, Address address, String image, String banner){
        this.phone = phone;
        this.name = name;
        this.image=image;
        this.banner = banner;
        this.address = address;
    }

    @Override
    public int compareTo(Restaurant restaurant) {
        return (int)(this.id - restaurant.getId());
    }
}
