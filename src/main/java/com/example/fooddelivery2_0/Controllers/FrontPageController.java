package com.example.fooddelivery2_0.Controllers;
import com.example.fooddelivery2_0.entities.ContactMessage;
import com.example.fooddelivery2_0.entities.Image;
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

    @GetMapping(path = "O-nama")
    public String aboutUs(Model model){
        model.addAttribute("banner", "images/o-nama.jpeg");
        model.addAttribute("beef", "images/beef.jpg");
        model.addAttribute("dough", "images/nuggets1.jpg");
        return "aboutUs";
    }
    @GetMapping(path = "Kontaktirajte-nas")
    public String contactUs(Model model){
        model.addAttribute("banner", "images/contact-us1.jpg");
        model.addAttribute("sendMessageToUs", new ContactMessage());
        return "contact-us";
    }
    @GetMapping(path = "/Kako-naručiti")
    public String howToOrder(Model model){
        model.addAttribute("banner", "images/howtoorder.jpg");
        return "howToOrder";
    }
    @GetMapping(path="/login")
    public String getLogin(){
        return "login";
    }
}


