package com.example.fooddelivery2_0.Controllers;
import com.example.fooddelivery2_0.entities.Order;
import com.example.fooddelivery2_0.entities.Restaurant;
import com.example.fooddelivery2_0.entities.RestaurantOwner;
import com.example.fooddelivery2_0.services.OrderRequestService;
import com.example.fooddelivery2_0.services.RestaurantService;
import com.example.fooddelivery2_0.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(path = "")
@AllArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final OrderRequestService orderRequestService;
    @GetMapping(path = "")
    public String getRestaurant(){
        return "about-us";
    }

    @GetMapping(path = "narudzbe")
    public String getRestaurant(Model model){
        RestaurantOwner owner = (RestaurantOwner)userService.getCurrentUser().get();

        Restaurant restaurant = restaurantService.getRestaurantByOwner(owner).get();
        model.addAttribute("item", restaurant);
        model.addAttribute("notif_ref", restaurant.getNotificationReference());

        List<Order> orders = orderRequestService.getNotDeliveredOrder(restaurant);
        Collections.sort(orders);

        model.addAttribute("orders", orders);
        //model.addAttribute("total", orderRequestService.getTotal(orders));
        return "restaurant_orders";
    }
    @GetMapping(path = "menu")
    public String getMenu(Model model){
        //System.out.println(restaurantService.getRestaurantByOwner((RestaurantOwner) userService.getCurrentUser().get()).get());
        Restaurant restaurant = restaurantService.getRestaurantByOwner((RestaurantOwner)userService.getCurrentUser().get()).get();
        model.addAttribute("menu",restaurant.getFoodItems());
        model.addAttribute("categories", restaurantService.getCategoriesFromRestaurant(restaurant.getFoodItems()));
        return "menu";
    }
    @GetMapping(path = "statistika")
    public String getStats(){
        return "stats";
    }

    @GetMapping(path = "stanje_narudzbe")
    public String getOrderProgress(){
        return "order_progress";
    }
    //TODO napravit service i repo za working hours, u kontroleru dobit od restorana sate, poslat ih na frontend
    //TODO i foreachat ih
}
