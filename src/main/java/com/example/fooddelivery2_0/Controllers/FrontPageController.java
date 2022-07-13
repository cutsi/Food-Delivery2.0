package com.example.fooddelivery2_0.Controllers;
import com.example.fooddelivery2_0.Utils.UserRole;
import com.example.fooddelivery2_0.entities.*;
import com.example.fooddelivery2_0.services.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// restoran food item 1 n
//restoran id vuce se kroy sve tablice
//portions food item 1 -> n
// price u food itemu visak
//admin
//report (Statiska)
// steps ne moze bit page kojem se ne moze pristuput
// filtering i searching nad svim restoranima (home page)
// i pojedini restoranima npr user moze vidit sve mijesane pizze iz svih restorana
// every food item is unique, every food name is unique,
// remove restaurants_food_items, add resturant_id to food_item
//
@Controller
@RequestMapping(path = "/")
@AllArgsConstructor
public class FrontPageController {
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final RatingService ratingService;
    private final WorkingHoursService workingHoursService;
    private final OrderService orderService;
    private final CityService cityService;
    private final FilterService filterService;
    private final OrderRequestService orderRequestService;
    private final FoodItemService foodItemService;

    @GetMapping(path = {"/", "/home"})
    public String home(Model model) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        String userRole = "";
        model.addAttribute("restaurants",restaurants);
        if(userService.getCurrentUser().isPresent()){
            userRole = userService.getCurrentUser().get().getUserRole().toString();
        }
        model.addAttribute("cities",cityService.getAllCityNames());
        model.addAttribute("userRole", userRole);
        return "home";
    }
    @GetMapping(path = "filter/{kod}/{grad}")
    public String homeFilter(Model model,@PathVariable("kod") String codeWord, @PathVariable("grad") String city){
        List<FoodItem> filteredFoodItems = filterService.filter(codeWord, city);
        if(filteredFoodItems.isEmpty()){
            model.addAttribute("message", "Nažalost, vaše pretraživanje nema rezultata.");
            return "filter-fail";
        }
        model.addAttribute("menu",filteredFoodItems);
        return "filter-meals";
    }

    @GetMapping(path = "grad")
    public String getRestaurantByCity(Model model, @RequestParam("ime") String city){
        System.out.println("GRAD: " + city);
        model.addAttribute("restaurants", restaurantService.getAllRestaurantsByCity(cityService.getCityByName(city).get()));
        model.addAttribute("cities", cityService.getAllCityNames());
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
    }//TODO unit podatke za restoran i jos jela i kategorija

    @GetMapping(path = "my-profile")
    public String myProfile(Model model){
        model.addAttribute("user", userService.getCurrentUser().get());
        System.out.println("userService.getCurrentUser().get(): = " + userService.getCurrentUser().get().getId());
        /*if(userService.getCurrentUser().get().getUserRole().equals(UserRole.CUSTOMER))
            model.addAttribute("orders", orderService.getAllOrdersByCustomerOrderByCreatedAtDesc((Customer) userService.getCurrentUser().get()));
        else if (userService.getCurrentUser().get().getUserRole().equals(UserRole.SUPER_RESTAURANT))
            model.addAttribute("orders", orderService.getAllByRestaurantOrderByCreatedAtDesc(restaurantService.getRestaurantByOwner((RestaurantOwner) userService.getCurrentUser().get()).get()));
        */
        Page<Order> orders = orderService.findPage(1, userService.getCurrentUser().get().getId());
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", orders.getTotalPages());
        model.addAttribute("totalItems", orders.getTotalElements());
        model.addAttribute("orders",orders);
        return "myProfile";
    }

    @GetMapping(path = "my-profile/{pageNumber}")
    public String myProfile(Model model, @PathVariable("pageNumber") int currentPage){
        Page<Order> orders = orderService.findPage(currentPage, userService.getCurrentUser().get().getId());
        model.addAttribute("user", userService.getCurrentUser().get());
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", orders.getTotalPages());
        model.addAttribute("totalItems", orders.getTotalElements());
        model.addAttribute("ordersActive", 1);
        return "myProfile";
    }

    /*@GetMapping("/my-profile/{pageNumber}")
    public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage){
        Page<Order> page = orderService.findPage(currentPage, userService.getCurrentUser().get().getId());
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<Order> orders = page.getContent();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("users", orders);
        model.addAttribute("buttonType", 1);
        return "users-pagination";
    }*/


    /*@GetMapping("/korisnici")
    public String getAllPages(Model model){
        return getOnePage(model, 1);
    }*/

    @GetMapping(path = "Trenutne-narudzbe")
    public String ongoing_orders(Model model){
        model.addAttribute("orders",orderRequestService.getActiveOrdersByCustomer((Customer)userService.getCurrentUser().get()));
        for (Order order:orderRequestService.getActiveOrdersByCustomer((Customer)userService.getCurrentUser().get())) {
            System.out.println("ONGOING ORDER: " + order.getStatus());
        }
        return "ongoing_orders";
    }

    //TODO onemogucit narucivanje kad je restoran zatvoren
    @GetMapping(path="/restaurant")
    public String restaurant(Model model, @RequestParam("ime") String restaurantId,
                             @RequestParam(required = false, name = "jelo") String clickedMeal) throws Exception {
        model.addAttribute("selectedFoodItemId", "none");

        if(restaurantId.contains("?jelo=")){
            String foodItemId = restaurantId.split(".?jelo=")[1];
            restaurantId = restaurantId.split(".?jelo=")[0];
            model.addAttribute("selectedFoodItemId", foodItemId);

        }
        Restaurant restaurant = restaurantService.getRestaurantByName(restaurantId).get();

        model.addAttribute("isFoodItemSelected", 1);
        model.addAttribute("categories", restaurantService.getCategoriesFromRestaurant(restaurant.getFoodItems()));
        model.addAttribute("menu",restaurant.getFoodItems());
        model.addAttribute("restaurant", restaurant);
        //System.out.println(workingHoursService.isRestaurantClosed(workingHoursService.getRestaurantWorkingHoursToday(restaurant)));
        //System.out.println(workingHoursService.restaurantClosed(workingHoursService.getRestaurantWorkingHoursToday(restaurant), restaurant));
        model.addAttribute("comments", ratingService.getAllApprovedRatingsByRestaurant(restaurant));
        //model.addAttribute("isClosed", workingHoursService.restaurantClosed(workingHoursService.getRestaurantWorkingHoursToday(restaurant), restaurant));
        model.addAttribute("isClosed", true);
        model.addAttribute("workingHours", workingHoursService.getByRestaurant(restaurant));
        model.addAttribute("workingHoursToday", workingHoursService.getRestaurantWorkingHoursToday(restaurant));
        model.addAttribute("numberOfReviews", ratingService.getAllByRestaurant(restaurant).size());
        model.addAttribute("averageRating", ratingService.getAverageRating(restaurant));
        return "restaurant";
    }//TODO popravit poziciju zvjezdica, omogucit komentiranje ulogiranim korisnicima, stavit zvjezdicu nad komentar

    @GetMapping("izaberi")
    @ResponseBody
    public Map<String, String> searchDataBase(Model model, @RequestParam("jelo") Long foodItemId){
        System.out.println("MEAL: " + foodItemId);
        Map<String, String> response = new HashMap<>();
        FoodItem foodItem = foodItemService.getById(foodItemId).get();
        Restaurant restaurant = restaurantService.getRestaurantById(foodItem.getRestaurant().getId()).get();

        response.put("path", "restaurant?ime=" + restaurant.getName() + "?jelo=" + foodItemId);
        return response;
    }
}


