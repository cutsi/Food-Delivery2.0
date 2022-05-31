package com.example.fooddelivery2_0.entities;
import lombok.*;
import net.bytebuddy.utility.RandomString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private String token;
    private boolean isExpired;
    private String info;
    @OneToOne
    private User user;

    public Token(User user) {
        this.token = RandomString.make(32);
        this.createdAt = LocalDateTime.now(ZoneId.of("CET"));
        this.expiresAt = LocalDateTime.now(ZoneId.of("CET")).plusMinutes(15);
        this.user = user;
    }
//
//    public boolean isTokenExpired() {
//        if(this.equals(null) || LocalDateTime.now(ZoneId.of("CET")).isAfter(expiresAt)){
//            return false;
//        }
//        return true;
//    }
}