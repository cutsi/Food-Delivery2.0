package com.example.fooddelivery2_0.Controllers;

import com.example.fooddelivery2_0.entities.Customer;
import com.example.fooddelivery2_0.entities.Rating;
import com.example.fooddelivery2_0.services.RatingService;
import com.example.fooddelivery2_0.services.RestaurantService;
import com.example.fooddelivery2_0.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/")
@AllArgsConstructor
public class RatingController {
    private final RatingService ratingService;
    private final UserService userService;
    private final RestaurantService restaurantService;

    @PostMapping(path = "komentiraj")
    public String comment(Model model, @RequestParam("restaurantName") String restaurant, @RequestParam("content") String content, @RequestParam("star") String star){
        var rating = new Rating(content,restaurantService.getRestaurantByName(restaurant).get(), (Customer) userService.getCurrentUser().get(),Integer.valueOf(star));
        ratingService.save(rating);
        model.addAttribute("Uspješno ste ostavili komentar");
        return "success";
    }
}
