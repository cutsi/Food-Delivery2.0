package com.example.fooddelivery2_0.Controllers;


import com.example.fooddelivery2_0.Utils.Requests.OrderRequest;
import com.example.fooddelivery2_0.entities.Customer;
import com.example.fooddelivery2_0.entities.Order;
import com.example.fooddelivery2_0.entities.RestaurantOwner;
import com.example.fooddelivery2_0.services.NotificationService;
import com.example.fooddelivery2_0.services.OrderRequestService;
import com.example.fooddelivery2_0.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = "/narudzba")
@AllArgsConstructor
public class OrderController {
    private final OrderRequestService orderRequestService;
    private final NotificationService notificationService;
    private final UserService userService;

    @PostMapping(path="", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String checkout(@RequestParam String[] foodItems,
                           @RequestParam Long restaurant_id,
                           Model model) {
        model.addAttribute("restaurantId", restaurant_id);
        model.addAttribute("foodItems", orderRequestService.getCartItems(foodItems));
        model.addAttribute("total", orderRequestService.addPrices(orderRequestService.getCartItems(foodItems)));
        return "checkout";
    }
    //what is this
    @PostMapping(path = "/new/{rid}",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> newOrder(@RequestBody OrderRequest orderRequest,
                                        @PathVariable("rid") Long restaurantId,
                                        Authentication principal) {

        System.out.println(orderRequest.getDeliveryNote());
        System.out.println(orderRequest.getRestaurantNote());

        Order order;
        Map<String, String> response = new HashMap<>();

        try {
            //SAVE NEW ORDER ON DATABASE
            order = orderRequestService.saveNewOrder(orderRequest, (Customer) principal.getPrincipal(), restaurantId);
            //PUSH NOTIF TO RESTAURANT
            notificationService.notifyRestaurantOnNewOrder(order);
            response.put("path","/narudzba/progress?refid="+order.getOrderReference()+order.getId());
            response.put("status","success");
        }catch (Exception e){
            response.put("path",null);
            response.put("status","failure");
            System.out.println(e);
        }

        return response;

    }
    //THIS HERE
    @GetMapping(path = "/progress")
    public String orderStatus(@RequestParam("refid") String refId, Model model) {

        //DO WHAT EVER YOU WANT WITH THE ORDER ID TO DISPLAY STEPS TO THE CLIENT
        Customer customer = (Customer)userService.getCurrentUser().get();
        Order order = orderRequestService.getOrderByRefId(refId, customer);
        if(order == null){
            throw new RuntimeException("Order not found or not authorized !");
        }else{
            model.addAttribute("status",order.getStatus().name());
            model.addAttribute("ref_id",refId);
            return "order_progress";
        }


    }
    //THIS HERE
    @GetMapping(path = "/orders/accept")
    @ResponseBody
    public Map<String, String> acceptOrder(@RequestParam("refid") String refId) {

        Map<String, String> response = new HashMap<>();
        RestaurantOwner owner = (RestaurantOwner)userService.getCurrentUser().get();
        Order order = orderRequestService.acceptOrder(refId, owner);
        if (order != null){
            notificationService.notifyCustomerOnOrderProgress(order);
            response.put("status","success");
        }else{
            response.put("status","error");
        }

        return response;

    }

    @GetMapping(path = "/orders/decline")
    @ResponseBody
    public Map<String, String> declineOrder(@RequestParam("refid") String refId) {

        Map<String, String> response = new HashMap<>();//why hashmap
        RestaurantOwner owner = (RestaurantOwner)userService.getCurrentUser().get();
        Order order = orderRequestService.declineOrder(refId, owner);
        if (order != null){
            notificationService.notifyCustomerOnOrderProgress(order);
            response.put("status","success");
        }else{
            response.put("status","error");
        }

        return response;

    }

    @GetMapping(path = "/orders/deliver")
    @ResponseBody
    public Map<String, String> deliverOrder(@RequestParam("refid") String refId) {

        Map<String, String> response = new HashMap<>();
        RestaurantOwner owner = (RestaurantOwner)userService.getCurrentUser().get();
        Order order = orderRequestService.deliverOrder(refId, owner);
        if (order != null){
            notificationService.notifyCustomerOnOrderProgress(order);
            response.put("status","success");
        }else{
            response.put("status","error");
        }

        return response;

    }

}

