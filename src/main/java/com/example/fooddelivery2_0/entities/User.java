package com.example.fooddelivery2_0.entities;
import com.example.fooddelivery2_0.Utils.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
@Inheritance(strategy =InheritanceType.JOINED)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    @JsonIgnore
    private Boolean isEnabled;

    private LocalDateTime createdAt;

    private String name;
    @JsonIgnore
    private String password;

    private String phone;
    @JsonIgnore
    private Boolean locked = false;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Token token;

    public User(String name, String email, String phone, String password) {
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.name = name;
        createdAt = LocalDateTime.now();
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {//daje rolu korisniku, za spring security da mozemo odlucit koja rola ima pristup cemu
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(this.userRole.name());
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;//return !locked
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public boolean hasRole(UserRole userRole) {
        return this.userRole.equals(userRole);
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
}


