package com.example.fooddelivery2_0.Utils.Requests;

import com.example.fooddelivery2_0.Utils.UserRole;
import lombok.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final Long id;
    private final String name;
    private final String phone;
    private final String email;
    private String password;
    private final String address;
    private final UserRole userRole;

    public void setPassword(String pass) {
        this.password = pass;
    }
}
