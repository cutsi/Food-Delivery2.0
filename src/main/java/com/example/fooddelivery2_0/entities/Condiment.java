package com.example.fooddelivery2_0.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@AllArgsConstructor
public class Condiment {
    @Id
    @GeneratedValue()
    private Long id;
    private Double price;
}