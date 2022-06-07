package com.example.fooddelivery2_0.Utils.Requests;

import com.example.fooddelivery2_0.entities.OrderContent;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private List<OrderContent> foodItems;
    private String deliveryNote;
    private String restaurantNote;
    private String phone;

}
