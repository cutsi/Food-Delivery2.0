package com.example.fooddelivery2_0.Controllers;
import com.example.fooddelivery2_0.Utils.FileUploadUtil;
import com.example.fooddelivery2_0.Utils.Requests.CondimentAddMealRequest;
import com.example.fooddelivery2_0.Utils.Requests.PortionAddMealRequest;
import com.example.fooddelivery2_0.entities.*;
import com.example.fooddelivery2_0.services.*;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
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
    private final ResponseService responseService;
    @GetMapping(path = "")
    public String getRestaurant(){
        return "about-us";
    }

    @GetMapping(path = "narudzbe")
    public String getRestaurant(Model model){
        var owner = (RestaurantOwner)userService.getCurrentUser().get();
        var restaurant = restaurantService.getRestaurantByOwner(owner).get();
        model.addAttribute("item", restaurant);
        model.addAttribute("notif_ref", restaurant.getNotificationReference());
        var orders = orderRequestService.getNotDeliveredOrder(restaurant.getId());
        Collections.sort(orders);
        model.addAttribute("orders", orders);
        return "restaurant_orders";
    }
    @GetMapping(path = "menu")
    public String getMenu(Model model){
        var restaurant = restaurantService.getRestaurantByOwner((RestaurantOwner)userService.getCurrentUser().get()).get();
        model.addAttribute("menu",restaurant.getFoodItems());
        model.addAttribute("categories", restaurantService.getCategoriesFromRestaurant(restaurant.getFoodItems()));
        return "menu";
    }
    @GetMapping(path = "statistika")
    public String getStats(Model model){
        var restaurantOwner = (RestaurantOwner)userService.getCurrentUser().get();
        var restaurant = restaurantService.getRestaurantById(restaurantOwner.getRestaurant().getId()).get();
        model.addAttribute("ordersStats",orderService.getOrdersStats(restaurant));
        model.addAttribute("customersCountList",orderService.getCustomersCountList());

        return "restaurant-stats";
    }
    @GetMapping(path = "komentari")
    public String getComments(Model model){
        model.addAttribute("ratings", ratingService.getRatingsWithNoResponseInTheLastThreeDays());
        model.addAttribute("oldRatings", ratingService.getAll());
        return "comments";
    }

    @PostMapping(path = "komentari/{id}")
    public String getCommentsPost(Model model, @PathVariable("id") String id){
        model.addAttribute("editComment", ratingService.getById(Long.valueOf(id)).get());
        model.addAttribute("ratings", ratingService.getRatingsWithNoResponseInTheLastThreeDays());
        model.addAttribute("oldRatings", ratingService.getAllByIsApproved());
        return getComments(model);
    }

    @PostMapping(path = "uredi-komentar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void editComment(@RequestBody String result){
        var ratingId = Long.valueOf(result.split("content")[0]);
        var content = result.split("content")[1];
        var rating = ratingService.getById(ratingId).get();
        var response = responseService.getById(rating.getResponse().getId()).get();
        response.setContent(content);
        responseService.save(response);
    }

    @PostMapping(path = "report", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void reportComment(@RequestBody String ratingId){
        var rating = ratingService.getById(Long.valueOf(ratingId)).get();
        rating.setIsReported(true);
        ratingService.save(rating);
    }

    @PostMapping(path = "/respond",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void respond(@RequestBody String result){
        var ratingId = result.split("=")[0];
        var responseContent = result.split("=")[1];
        var rating = ratingService.getById(Long.valueOf(ratingId)).get();
        var response = new Response(responseContent, rating, restaurantService.getRestaurantByOwner(
                        (RestaurantOwner) userService.getCurrentUser().get()).get());
        responseService.save(response);
        ratingService.save(rating);
    }
    @GetMapping(path = "edit")
    public String editMeal(Model model,@RequestParam("itemId") String itemId){
        model.addAttribute("foodItem", foodItemService.getById(Long.valueOf(itemId)).get());
        model.addAttribute("condiments", condimentService.getAllCondimentsByRestaurant(foodItemService.getById(Long.valueOf(itemId)).get().getRestaurant()));
        return "editMeal";
    }

    @PostMapping(path = "edit")
    public String editMealPost(Model model,@RequestParam("portion") String portion){
        var restaurant = restaurantService.getRestaurantByOwner((RestaurantOwner)userService.getCurrentUser().get()).get();
        model.addAttribute("menu",restaurant.getFoodItems());
        model.addAttribute("categories", restaurantService.getCategoriesFromRestaurant(restaurant.getFoodItems()));
        return "menu";
    }

    @PostMapping(path = "dodaj-jelo")
    public String addNewMealPost(Model model, @RequestParam("info") String info,
                                 @RequestParam("name") String name, @RequestParam("category") String category,
                                 @ModelAttribute PortionAddMealRequest portions,
                                 @RequestParam("image") MultipartFile multipartFile,
                                 @ModelAttribute CondimentAddMealRequest condiments) throws IOException {
        var restaurant = restaurantService.getRestaurantByOwner((RestaurantOwner)userService.getCurrentUser().get()).get();
        var uploadDir = "C:\\Users\\josip\\Documents\\workspace-spring-tool-suite-4-4.10.0.RELEASE\\FoodDelivery2.0\\src\\main\\resources\\static\\images";
        var fileNameImage = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        FileUploadUtil.saveFile(uploadDir, fileNameImage, multipartFile);
        var foodItem = new FoodItem("images/"+fileNameImage, name, info, categoryService.getCategoryByName(category).get());
        foodItem.setRestaurant(restaurant);
        foodItemService.save(foodItem);
        foodItem.setPortions(portionService.savePortions(portions.getPortionName(),portions.getPrice(), foodItem));
        condimentService.saveCondiments(condiments.getCondimentName(),condiments.getCondimentPrice(), foodItem);
        foodItemService.save(foodItem);
        model.addAttribute("foodItem",foodItemService.getById(foodItem.getId()).get());
        return "setDefaultPrice";
    }

    @GetMapping(path = "dodaj-jelo")
    public String addNewMeal(Model model){
        var restaurantOwner = (RestaurantOwner) userService.getCurrentUser().get();
        model.addAttribute("portion", new Portion());
        model.addAttribute("condiment", new Condiment());
        model.addAttribute("condiments", condimentService.getAllCondimentsByRestaurant(restaurantOwner.getRestaurant()));
        model.addAttribute("portions", portionService.getAllPortionNames());
        model.addAttribute("categories", categoryService.getAll());

        return "addMeal";
    }

    @GetMapping(path = "izaberi-cijenu/{foodItemId}")
    public String selectDefaultPrice(Model model, @PathVariable("foodItemId") Long foodItemId){
        model.addAttribute("foodItem", foodItemService.getById(foodItemId).get());
        return "setDefaultPrice";
    }

    @PostMapping(path = "spremi-porciju", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void saveDefaultPrice(@RequestBody String data){
        var foodItemId = data.split("=")[0];
        var portionNameId = data.split("=")[1];
        var portion = portionService.getByNameIdAndFoodItem(Long.valueOf(portionNameId), foodItemService.getById(Long.valueOf(foodItemId)).get().getId());
        portion.setChecked(true);
        portionService.save(portion);

    }

    @GetMapping(path = "dodaj-dodatak")
    public String addCondiment(){
        return "addCondiment";
    }

    @GetMapping(path = "stanje_narudzbe")
    public String getOrderProgress(){
        return "order_progress";
    }

    @PostMapping(path = "sakrij-jelo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void hideMeal(@RequestBody String result){
        var foodItem = foodItemService.getById(Long.valueOf(result.split("action=")[0])).get();
        if(result.split("action=")[1].equals("hide")){
            foodItem.setIsHidden(true);
            foodItemService.save(foodItem);
        }
        if(result.split("action=")[1].equals("show")){
            foodItem.setIsHidden(false);
            foodItemService.save(foodItem);
        }
    }
}
