package com.example.fooddelivery2_0.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue()
    private Long id;
    private String name;
}
