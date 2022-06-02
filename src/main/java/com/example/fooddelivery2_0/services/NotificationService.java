package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.Order;
import com.example.fooddelivery2_0.entities.Restaurant;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {

    /*private final SimpMessagingTemplate simpMessagingTemplate;

    public void notifyRestaurantOnNewOrder(Order order){
        Restaurant restaurant = order.getRestaurant();
        restaurant.getOwners().stream()
                .forEach(o-> {
                    System.out.println(o.getUsername());
                    //impMessagingTemplate.convertAndSend("Hello");
                    simpMessagingTemplate.convertAndSendToUser(
                            restaurant.getNotificationReference()+restaurant.getId(),
                            "restaurant-notifications/new-order",
                            order
                    );
                });
    }
    public void notifyCustomerOnOrderProgress(Order order){
        simpMessagingTemplate.convertAndSendToUser(
                order.getOrderReference()+order.getId(),
                "order/progress",
                order.getStatus().name()
        );// user listens in the first two lines, third line is what we send
    }*/

}
