package com.example.fooddelivery2_0.Utils.Requests;

import lombok.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PortionAddMealRequest {
    private String portionName;
    private String price;

    public PortionAddMealRequest() {

    }

}
