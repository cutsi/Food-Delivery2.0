package com.example.fooddelivery2_0.Utils.Requests;

import com.example.fooddelivery2_0.entities.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRestaurantRequest {
    private String name;
    private String phone;
    private String address;
    private String city;
}
