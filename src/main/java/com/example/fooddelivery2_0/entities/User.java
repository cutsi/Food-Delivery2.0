package com.example.fooddelivery2_0.entities;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue()
    private Long id;
    private String email;
    private Boolean isEnabled;
    private String name;
    private String password;
    private String phone;
    private Boolean locked;

    @OneToOne(cascade = CascadeType.ALL )
    private Token token;

    @ManyToOne
    private Role role;


}


