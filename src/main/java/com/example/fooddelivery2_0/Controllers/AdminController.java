package com.example.fooddelivery2_0.Controllers;

import com.example.fooddelivery2_0.Utils.FileUploadUtil;
import com.example.fooddelivery2_0.Utils.Requests.CreateRestaurantRequest;
import com.example.fooddelivery2_0.Utils.UserRole;
import com.example.fooddelivery2_0.entities.*;
import com.example.fooddelivery2_0.repos.EmployeeFunctionRepo;
import com.example.fooddelivery2_0.services.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/admin")
@AllArgsConstructor
public class AdminController {
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final EmployeeFunctionRepo employeeFunctionRepo;
    private final RestaurantOwnerService restaurantOwnerService;
    private final AddressService addressService;
    private final CityService cityService;
    private final RatingService ratingService;
    private final OrderRequestService orderRequestService;
    private final OrderService orderService;
    private final CustomerService customerService;
    @GetMapping
    public String getAdmin(Model model){
        model.addAttribute("ratings", ratingService.getAllReportedComments());
        model.addAttribute("restaurants",orderRequestService.getRestaurantsAndOrdersByRestaurantToday());
        model.addAttribute("customers", customerService.getTopOneHundredCustomersWithNumberOfOrders());
        model.addAttribute("newCustomers", userService.getNewCustomers());
        return "admin";
    }

    @GetMapping(path = "profil-korisnika/{id}")
    public String getCustomerProfile(Model model, @PathVariable("id") Long id){
        User user = userService.getUserById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("orders", orderService.getAllOrdersByCustomerOrderByCreatedAtDesc((Customer) user));
        return "myProfile";
    }

    @GetMapping("/restaurant/stats/{restId}")
    public String adminDemo(Model model, @PathVariable Long restId){

        //TODO check if restaurant exists
        Restaurant restaurant = restaurantService.getRestaurantById(restId).get();
        model.addAttribute("ordersStats",orderService.getOrdersStats(restaurant));
        model.addAttribute("customersCountList",orderService.getCustomersCountList());
        return "restaurant-stats";
    }

    @GetMapping("dodaj-restoran")
    public String addNewRestaurant(Model model){
        model.addAttribute("cities", cityService.getAllCityNames());
        model.addAttribute("restaurant", new Restaurant());
        return "dodaj-restoran";
    }

    @PostMapping(path = "save/restaurant")
    public String saveRestaurant(@ModelAttribute CreateRestaurantRequest rq,
                                 @RequestParam("image") MultipartFile multipartFile,
                                 @RequestParam("banner") MultipartFile multipartFileBanner,
                                 @RequestParam(value = "ownerId", required = false, defaultValue = "") String ownerId) throws IOException {
        String uploadDir = "C:\\Users\\josip\\Documents\\workspace-spring-tool-suite-4-4.10.0.RELEASE\\FoodDelivery2.0\\src\\main\\resources\\static\\images";
        String fileNameImage = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String fileNameBanner = StringUtils.cleanPath(multipartFileBanner.getOriginalFilename());
        Address address = new Address(rq.getAddress(), cityService.getCityByName(rq.getCity()).get());
        addressService.save(address);

        Restaurant restaurant = new Restaurant(rq.getPhone(),rq.getName(),
                address,"images/"+fileNameImage, "images/"+fileNameBanner);


        if(!ownerId.equals("")){
            if(userService.getUserById(Long.valueOf(ownerId)).isPresent() ||
                userService.getUserById(Long.valueOf(ownerId)).get().getUserRole().equals(UserRole.SUPER_RESTAURANT.toString())){
                RestaurantOwner restaurantOwner = (RestaurantOwner) userService.getUserById(Long.valueOf(ownerId)).get();
                restaurantOwner.setRestaurant(restaurant);
                restaurantOwner.setFunction(employeeFunctionRepo.findByName("Manager").get());
                restaurantOwnerService.save(restaurantOwner);
            }
        }
        restaurantService.save(restaurant);
        FileUploadUtil.saveFile(uploadDir, fileNameImage, multipartFile);
        FileUploadUtil.saveFile(uploadDir,fileNameBanner,multipartFileBanner);
        return "admin";
    }

    @PostMapping(path = "odobri-komentar")
    public String approveRating(Model model,@RequestParam String approveId){
        Rating rating = ratingService.getById(Long.valueOf(approveId)).get();
        rating.setIsApproved(true);
        ratingService.save(rating);

        model.addAttribute("ratings", ratingService.getAllByIsApprovedFalse());
        model.addAttribute("restaurants",orderRequestService.getRestaurantsAndOrdersByRestaurantToday());
        return "admin";
    }

    @PostMapping(path = "izbrisi-komentar")
    public String declineRating(Model model,@RequestParam String approveId){
        ratingService.delete(ratingService.getById(Long.valueOf(approveId)).get());
        model.addAttribute("ratings", ratingService.getAllByIsApprovedFalse());
        model.addAttribute("restaurants",orderRequestService.getRestaurantsAndOrdersByRestaurantToday());
        return "admin";
    }

    @PostMapping(path = "/approve",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void approve(@RequestBody String result){
        String approveId = result.split("=")[0];
        String decision = result.split("=")[1];
        Rating rating = ratingService.getById(Long.valueOf(approveId)).get();
        if(decision.equals("decline")){
            rating.setIsApproved(false);
            rating.setIsReported(false);
            ratingService.save(rating);
            return;
        }
        rating.setIsReported(false);
        ratingService.save(rating);
    }
    @GetMapping(path = "svi-korisnici")
    public String getAll(){
        List<User> users;
        users = userService.getAllUsers();
        return "users-pagination";

    }
    @GetMapping("/korisnici/stranica/{pageNumber}")
    public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage){
        Page<User> page = userService.findPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<User> users = page.getContent();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("users", users);

        return "users-pagination";
    }

    @GetMapping("/korisnici")
    public String getAllPages(Model model){
        return getOnePage(model, 1);
    }

    @GetMapping("pretrazi-bazu")
    @ResponseBody
    public void searchDataBase(Model model, @RequestParam("user") String keyword){
        System.out.println("KEYWORD: " + keyword);
    }
}
