package com.example.fooddelivery2_0.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private Boolean isApproved;
    private Integer rating;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Response response;

    @ManyToOne
    private Restaurant restaurant;

    public Rating(String content, Restaurant restaurant, Customer user, Integer rating) {
        this.content = content;
        this.restaurant = restaurant;
        this.customer = user;
        this.rating = rating;
        this.createdAt = LocalDateTime.now(ZoneId.of("CET"));
        this.isApproved = false;
    }

//    private Long responseId;
//
//    @ManyToOne(cascade = { CascadeType.REMOVE }) //one user can have many comments
//    @JoinColumn(
//            nullable = false,
//            name = "user_id"
//    )
//    private User user;
//    @ManyToOne(cascade = { CascadeType.REMOVE }) //one user can have many comments
//    @JoinColumn(
//            nullable = false,
//            name = "restaurant_id"
//    )
//    private Restaurant restaurant;
//
//    public Comment(String content, Restaurant restaurant, User user, Integer rating){
//        this.user = user;
//        this.content = content;
//        this.createdAt = LocalDateTime.now(ZoneId.of("CET"));
//        this.isApproved = false;
//        this.restaurant = restaurant;
//        this.rating = rating;
//    }
}


