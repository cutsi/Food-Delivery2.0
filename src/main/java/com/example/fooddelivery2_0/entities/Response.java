package com.example.fooddelivery2_0.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "responses")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "response")
    @JsonIgnore
    private Rating rating;

    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;

    public Response(String responseContent,Rating rating, Restaurant restaurant) {
        this.content=responseContent;
        this.createdAt = LocalDateTime.now();
        this.rating = rating;
        this.restaurant=restaurant;
    }
}
