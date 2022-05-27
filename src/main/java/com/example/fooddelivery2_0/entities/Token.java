package com.example.fooddelivery2_0.entities;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Token {
    @Id
    @GeneratedValue()
    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean isExpired;
    private String info;
}