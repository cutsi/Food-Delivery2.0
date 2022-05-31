package com.example.fooddelivery2_0.entities;
import javax.persistence.Entity;

import lombok.*;

import javax.persistence.ManyToOne;
import javax.persistence.Table;
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurant_owners")
public class RestaurantOwner extends User{
    @ManyToOne
    private EmployeeFunction function;
    @ManyToOne
    private Restaurant restaurant;

    public RestaurantOwner(String name, String email, String phone, String password) {
        super(name,email,phone,password);
    }

}
