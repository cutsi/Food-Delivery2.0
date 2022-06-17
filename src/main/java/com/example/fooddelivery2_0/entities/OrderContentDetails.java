package com.example.fooddelivery2_0.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_content_details")
public class OrderContentDetails {//should be removed

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonIgnore
    @Transient
    private String uniqueId;
    private int quantity;
    private String price;
    @ManyToOne
    private Portion portion;
    @ManyToMany
//    @JoinTable(name="order_contents_cond", joinColumns=@JoinColumn(name="order_content_id"),
//            inverseJoinColumns=@JoinColumn(name="condiment_id"))
    private List<Condiment> condiments;

}
