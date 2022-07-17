package com.example.fooddelivery2_0.Controllers;


import com.example.fooddelivery2_0.Utils.Requests.OrderRequest;
import com.example.fooddelivery2_0.entities.Customer;
import com.example.fooddelivery2_0.entities.RestaurantOwner;
import com.example.fooddelivery2_0.services.NotificationService;
import com.example.fooddelivery2_0.services.OrderRequestService;
import com.example.fooddelivery2_0.services.OrderService;
import com.example.fooddelivery2_0.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping(path = "/narudzba")
@AllArgsConstructor
public class OrderController {
    private final OrderRequestService orderRequestService;
    private final NotificationService notificationService;
    private final UserService userService;
    private final OrderService orderService;

    @PostMapping(path="", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String checkout(@RequestParam String[] foodItems,
                           @RequestParam Long restaurant_id,
                           Model model) {
        model.addAttribute("restaurantId", restaurant_id);
        model.addAttribute("foodItems", orderRequestService.getCartItems(foodItems));
        model.addAttribute("total", orderRequestService.addPrices(orderRequestService.getCartItems(foodItems)));
        return "checkout";
    }
    @PostMapping(path = "/new/{rid}",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HashMap<Object, Object> newOrder(@RequestBody OrderRequest orderRequest,
                                            @PathVariable("rid") Long restaurantId,
                                            Authentication principal) {
        var response = new HashMap<>();
        try {
            var order = orderRequestService.saveNewOrder(orderRequest, (Customer) principal.getPrincipal(), restaurantId);//ASK
            order.setPrice(orderRequestService.addPrices(order.getContents()));
            orderService.save(order);
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
    @GetMapping(path = "/progress")
    public String orderStatus(@RequestParam("refid") String refId, Model model) {
        var customer = (Customer)userService.getCurrentUser().get();
        var order = orderRequestService.getOrderByRefId(refId, customer);
        if(order == null){
            throw new RuntimeException("Order not found or not authorized !");
        }else{
            model.addAttribute("status",order.getStatus().name());
            model.addAttribute("ref_id",refId);
            return "order_progress";
        }


    }
    @GetMapping(path = "/orders/accept")
    @ResponseBody
    public HashMap<Object, Object> acceptOrder(@RequestParam("refid") String refId) {

        var response = new HashMap<>();
        var owner = (RestaurantOwner)userService.getCurrentUser().get();
        var order = orderRequestService.acceptOrder(refId, owner);
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
    public HashMap<Object, Object> declineOrder(@RequestParam("refid") String refId) {
        var response = new HashMap<>();
        var message = "Nažalost nismo u mogućnosti trenutno dostaviti vašu narudžbu";
        try{
            message = refId.split("message=")[1];
        }catch (Exception e){

        }
        var owner = (RestaurantOwner)userService.getCurrentUser().get();
        var order = orderRequestService.declineOrder(refId.split("message=")[0], owner);
        if (order != null){
            notificationService.notifyCustomerOnOrderDecline(order,message);
            response.put("status","success");
        }else{
            response.put("status","error");
        }

        return response;

    }

    @GetMapping(path = "/orders/deliver")
    @ResponseBody
    public HashMap<Object, Object> deliverOrder(@RequestParam("refid") String refId) {
        var response = new HashMap<>();
        var owner = (RestaurantOwner)userService.getCurrentUser().get();
        var order = orderRequestService.deliverOrder(refId, owner);
        if (order != null){
            notificationService.notifyCustomerOnOrderProgress(order);
            response.put("status","success");
        }else{
            response.put("status","error");
        }
        return response;
    }
}

