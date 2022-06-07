package com.example.fooddelivery2_0.Controllers;
import com.example.fooddelivery2_0.entities.ContactMessage;
import com.example.fooddelivery2_0.entities.Image;
import com.example.fooddelivery2_0.entities.Restaurant;
import com.example.fooddelivery2_0.services.*;
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
    private final RatingService ratingService;
    private final WorkingHoursService workingHoursService;
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

    @GetMapping(path = "my-profile")
    public String myProfile(Model model){
        model.addAttribute("user", userService.getCurrentUser().get());
        return "myProfile";
    }

    //TODO onemogucit narucivanje kad je restoran zatvoren
    @GetMapping(path="/restaurant")
    public String restaurant(Model model, @RequestParam("ime") String restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByName(restaurantId).get();
        model.addAttribute("categories", restaurantService.getCategoriesFromRestaurant(restaurant.getFoodItems()));
        model.addAttribute("menu",restaurant.getFoodItems());
        model.addAttribute("restaurant", restaurant);
        //System.out.println(workingHoursService.isRestaurantClosed(workingHoursService.getRestaurantWorkingHoursToday(restaurant)));
        //System.out.println(workingHoursService.restaurantClosed(workingHoursService.getRestaurantWorkingHoursToday(restaurant), restaurant));
        model.addAttribute("comments", ratingService.getAllByRestaurant(restaurant));
        //model.addAttribute("isClosed", workingHoursService.restaurantClosed(workingHoursService.getRestaurantWorkingHoursToday(restaurant), restaurant));
        model.addAttribute("isClosed", true);
        model.addAttribute("workingHours", workingHoursService.getByRestaurant(restaurant));
        model.addAttribute("workingHoursToday", workingHoursService.getRestaurantWorkingHoursToday(restaurant));
        model.addAttribute("numberOfReviews", ratingService.getAllByRestaurant(restaurant).size());
        model.addAttribute("averageRating", ratingService.getAverageRating(restaurant));
        return "restaurant";
    }//TODO popravit poziciju zvjezdica, omogucit komentiranje ulogiranim korisnicima, stavit zvjezdicu nad komentar
}


