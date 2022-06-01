package com.example.fooddelivery2_0.Components;

import com.example.fooddelivery2_0.Utils.UserRole;
import com.example.fooddelivery2_0.entities.User;
import com.example.fooddelivery2_0.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        //CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = userService.getCurrentUser().get();
        String redirectURL = request.getContextPath();

        if (currentUser.hasRole(UserRole.CUSTOMER)) {
            redirectURL = "/";
        } else if (currentUser.hasRole(UserRole.RESTAURANT) || currentUser.hasRole(UserRole.SUPER_RESTAURANT)) {
            redirectURL = "narudzbe";
        } else if (currentUser.hasRole(UserRole.ADMIN)) {
            redirectURL = "/";
        }

        response.sendRedirect(redirectURL);

    }
}
