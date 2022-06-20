package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.Customer;
import com.example.fooddelivery2_0.entities.Order;
import com.example.fooddelivery2_0.entities.OrderContent;
import com.example.fooddelivery2_0.entities.Restaurant;
import com.example.fooddelivery2_0.repos.OrderRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;

    public String[] serialize(String[] foodItems){

        String orderStr = new String();
        String fullOrderStr = new String();
        String[] fullOrderArr = new String[foodItems.length];
        for (int i = 0; i< foodItems.length-1;i++) {
            for(int j = foodItems[i].indexOf("orderDetail")+14; j<foodItems[i].length()-1;j++){
                orderStr += foodItems[i].charAt(j);
            }
            for(int k = 0;  k<foodItems[i].indexOf("orderDetail")-1;k++){
                fullOrderStr += foodItems[i].charAt(k);
            }
            System.out.println("FULLORDERSTR: " + fullOrderStr);
            System.out.println("ORDERSTR: " + orderStr);
            fullOrderArr[i] = fullOrderStr + orderStr;
        }
        return fullOrderArr;
    }
    public List<Order> getAllOrders(){
        return orderRepo.findAll();
    }
    public List<Order> getAllOrdersByCustomer(Customer customer){
        return orderRepo.findAllByCustomer(customer);
    }
    public List<Order> getAllOrdersByCustomerOrderByCreatedAtDesc(Customer customer){
        return orderRepo.findAllByCustomerOrderByCreatedAtDesc(customer);
    }
    public List<Order> getAllByRestaurant(Restaurant restaurant){
        return orderRepo.findAllByRestaurant(restaurant);
    }
    public List<Order> getAllByRestaurantOrderByCreatedAtDesc(Restaurant restaurant){
        return orderRepo.findAllByRestaurantOrderByCreatedAtDesc(restaurant);
    }

    public void save(Order order) {
        orderRepo.save(order);
    }


}
