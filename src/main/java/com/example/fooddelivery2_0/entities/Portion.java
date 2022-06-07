package com.example.fooddelivery2_0.entities;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "portions")
public class Portion implements Comparable<Portion>{ //DONE

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String price;
    @Column(nullable = true)
    private boolean isChecked;
    @ManyToOne
    private PortionName name;

    public Portion(String price, boolean isChecked, PortionName portionName) {
        this.price = price;
        this.isChecked = isChecked;
        this.name = portionName;
    }

    @Override
    public int compareTo(Portion portion) {
        return (int) (this.id - portion.getId());
    }
}
