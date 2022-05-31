package com.example.fooddelivery2_0.entities;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String opensAt;
    private String closesAt;
    private String dayOfWeek;
    @ManyToOne
    private Restaurant restaurant;

    public WorkingHours(String opensAt, String closesAt, String dayOfWeek, Restaurant restaurant) {
        this.opensAt = opensAt;
        this.closesAt = closesAt;
        this.dayOfWeek = dayOfWeek;
        this.restaurant = restaurant;
    }

    //    @ManyToOne
//    @JoinColumn(name="restaurant_id")
//    private Restaurant restaurant;
}
