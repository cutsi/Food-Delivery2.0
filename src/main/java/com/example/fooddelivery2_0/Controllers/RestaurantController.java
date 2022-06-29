package com.example.fooddelivery2_0.Controllers;
import com.example.fooddelivery2_0.entities.*;
import com.example.fooddelivery2_0.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(path = "")
@AllArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final OrderRequestService orderRequestService;
    private final FoodItemService foodItemService;
    private final CondimentService condimentService;
    private final PortionService portionService;
    private final CategoryService categoryService;
    private final OrderService orderService;
    private final RatingService ratingService;
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

        List<Order> orders = orderRequestService.getNotDeliveredOrder(restaurant.getId());
        Collections.sort(orders);

        orders.stream()
                .forEach(o-> {
                    System.out.printf("Rest id : "+o.getRestaurant().getId());
                });

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
    public String getStats(Model model){
        RestaurantOwner restaurantOwner = (RestaurantOwner)userService.getCurrentUser().get();
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantOwner.getRestaurant().getId()).get();
        model.addAttribute("ordersStats",orderService.getOrdersStats(restaurant));
        model.addAttribute("customersCountList",orderService.getCustomersCountList());

        return "restaurant-stats";
    }
    @GetMapping(path = "komentari")
    public String getComments(Model model){
        for (Rating rating:ratingService.getRatingsWithNoResponse()) {
            System.out.println(rating.getContent());
        }
        model.addAttribute("oldRatings", ratingService.getRatingsWithNoResponse());
        model.addAttribute("ratings", ratingService.getAllByIsApproved());
       // System.out.println(ratingService.getAllByIsApproved());
        return "comments";
    }

    @GetMapping(path = "edit")
    public String editMeal(Model model,@RequestParam("itemId") String itemId){
        for (Condiment condiment: condimentService.getAllCondimentsByRestaurant(foodItemService.getById(Long.valueOf(itemId)).get().getRestaurant())) {
            System.out.println("condiment: " + condiment.getName().getName());

        }
        model.addAttribute("foodItem", foodItemService.getById(Long.valueOf(itemId)).get());
        model.addAttribute("condiments", condimentService.getAllCondimentsByRestaurant(foodItemService.getById(Long.valueOf(itemId)).get().getRestaurant()));
        return "editMeal";
    }

    @PostMapping(path = "edit")
    public String editMealPost(Model model,@RequestParam("portion") String portion){
        System.out.println("ITEMID: " + portion);

        Restaurant restaurant = restaurantService.getRestaurantByOwner((RestaurantOwner)userService.getCurrentUser().get()).get();
        model.addAttribute("menu",restaurant.getFoodItems());
        model.addAttribute("categories", restaurantService.getCategoriesFromRestaurant(restaurant.getFoodItems()));
        return "menu";
    }

    @GetMapping(path = "dodaj-jelo")
    public String addNewMeal(Model model){
        RestaurantOwner restaurantOwner = (RestaurantOwner) userService.getCurrentUser().get();
        model.addAttribute("condiments", condimentService.getAllCondimentsByRestaurant(restaurantOwner.getRestaurant()));
        model.addAttribute("portions", portionService.getAllPortionNames());
        model.addAttribute("categories", categoryService.getAll());

        return "addMeal";
    }

    @GetMapping(path = "dodaj-dodatak")
    public String addCondiment(){
        return "addCondiment";
    }

    @GetMapping(path = "stanje_narudzbe")
    public String getOrderProgress(){
        return "order_progress";
    }
    //TODO napravit service i repo za working hours, u kontroleru dobit od restorana sate, poslat ih na frontend
    //TODO i foreachat ih
}
