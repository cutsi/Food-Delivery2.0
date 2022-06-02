package com.example.fooddelivery2_0.Controllers;
import com.example.fooddelivery2_0.entities.Restaurant;
import com.example.fooddelivery2_0.services.ImageService;
import com.example.fooddelivery2_0.services.RestaurantService;
import com.example.fooddelivery2_0.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping(path = "/")
@AllArgsConstructor
public class FrontPageController {
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final ImageService imageService;
    @GetMapping(path = {"/", "/home"})
    public String home(Model model){
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        String userRole = "";
        model.addAttribute("restaurants",restaurants);
        if(userService.getCurrentUser().isPresent()){
            userRole = userService.getCurrentUser().get().getUserRole().toString();
        }
        model.addAttribute("userRole", userRole);
        return "home";
    }

    @GetMapping(path = "o-nama")
    public String aboutUs(Model model){
        model.addAttribute("banner", imageService.getImageById(7L).get().getName());
        model.addAttribute("beef", imageService.getImageById(3L).get().getName());
        model.addAttribute("dough", imageService.getImageById(5L).get().getName());
        return "aboutUs";
    }
}


