package com.example.fooddelivery2_0.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
    private Boolean isApproved = true;
    private Integer rating;
    private Boolean isReported = false;
    @JsonBackReference("customer-rating")
    @ManyToOne
    private Customer customer;

    @OneToOne
    private Response response;

    @JsonBackReference(value = "restaurant-ratings")
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
    public Rating(String content) {
        this.content = content;
    }
    public String getCreatedAtDMYHM(){
        String dayOfMonth = String.valueOf(createdAt.getDayOfMonth());
        String month = String.valueOf(createdAt.getMonthValue());
        String hour = String.valueOf(createdAt.getHour());
        String minute = String.valueOf(createdAt.getMinute());

        if(createdAt.getDayOfMonth()<10){
            dayOfMonth = '0' + String.valueOf(createdAt.getDayOfMonth());
        }
        if(createdAt.getMonthValue()<10){
            month = '0' + String.valueOf(createdAt.getMonthValue());
        }
        if(createdAt.getHour()<10){
            hour = '0' + String.valueOf(createdAt.getHour());
        }
        if(createdAt.getMinute()<10){
            minute = '0'+ String.valueOf(createdAt.getMinute());
        }

        return  dayOfMonth + "." + month + "." + createdAt.getYear() + " " + hour + ":" + minute;
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


