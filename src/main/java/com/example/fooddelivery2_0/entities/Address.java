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
public class Address {
    @Id
    @GeneratedValue()
    private Long id;
    private String name;
    private String city;
    private String country;
}