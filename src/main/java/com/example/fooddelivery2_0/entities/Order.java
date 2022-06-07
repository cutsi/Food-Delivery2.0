package com.example.fooddelivery2_0.entities;

import com.example.fooddelivery2_0.Utils.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Comparable<Order>{ //DONE

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orderReference;
    private String restaurantNote;
    private String deliveryNote;
    private String address;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Status status;
    private boolean isAccepted;
    private boolean isDeclined;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderContent> contents;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Order(String orderReference, Customer customer, Restaurant restaurant, List<OrderContent> contents, Status status) {
        this.orderReference = orderReference;
        this.status = status;
        this.customer = customer;
        this.restaurant = restaurant;
        this.contents = contents;
    }
    public String getCreatedAtLDT(){
        return createdAt.getHour() + ":" + createdAt.getMinute();
    }
    public List<OrderContent> getContents(){
        return contents;
    }
    @Override
    public int compareTo(Order o) {
        return this.createdAt.compareTo(o.getCreatedAt());
    }
}