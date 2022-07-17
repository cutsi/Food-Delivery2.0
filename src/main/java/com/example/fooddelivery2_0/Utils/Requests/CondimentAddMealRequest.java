package com.example.fooddelivery2_0.Utils.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CondimentAddMealRequest {
    private String condimentName;
    private String condimentPrice;

    public CondimentAddMealRequest() {

    }

}