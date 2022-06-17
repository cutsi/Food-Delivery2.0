package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.Utils.ReferenceGenerator;
import com.example.fooddelivery2_0.Utils.Requests.OrderRequest;
import com.example.fooddelivery2_0.Utils.Status;
import com.example.fooddelivery2_0.entities.*;
import com.example.fooddelivery2_0.repos.OrderContentDetailsRepo;
import com.example.fooddelivery2_0.repos.OrderContentRepo;
import com.example.fooddelivery2_0.repos.OrderRepo;
import com.example.fooddelivery2_0.repos.RestaurantRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class OrderRequestService {
    private OrderRepo orderRepo;
    private RestaurantRepo restaurantRepo;
    private OrderContentRepo orderContentRepo;
    private OrderContentDetailsRepo orderContentDetailRepo;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public String getTotal(List<Order> orders){
        Double totalPrice = 0.00;
        for (Order order:orders) {
            for(OrderContent orderContent: order.getContents()){
                totalPrice = totalPrice + Double.valueOf(orderContent.getPortion().getPrice());//CHANGE
            }
        }
        System.out.println("TOTAL PRICE_: " + totalPrice);
        return totalPrice.toString();
    }
    public String addPrices(List<OrderContent> cartItems){
        return df.format(cartItems.stream()
                .mapToDouble(cartItem -> Double.parseDouble(cartItem.getPortion().getPrice()))//CHANGE
                .sum());
    }

    public List<OrderContent> getCartItems(String[] foodItems){
        List<OrderContent> cartItems = new ArrayList<OrderContent>();

        ObjectMapper mapper = new ObjectMapper();
        Stream.of(foodItems).forEach(f->{
            try {

                if(!"null".equals(f)) {
                    System.out.println("STREAM OF:" + f);
                    System.out.println("OrderContent.class: " + OrderContent.class);
                    cartItems.add(mapper.readValue(f, OrderContent.class));
                }

            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });
        return cartItems;
    }
    @Transient
    public Order saveNewOrder(OrderRequest order, Customer customer, Long restaurantId) throws NoSuchAlgorithmException {

        /*order.getFoodItems().stream().forEach(p->{
            orderContentDetailRepo.save(p.getOrderDetail());//TO CHANGE
        });*/
        List<OrderContent> contents = orderContentRepo.saveAll(order.getFoodItems());
        Order newOrder = new Order(ReferenceGenerator.generateReference(), customer, restaurantRepo.getById(restaurantId), contents,  Status.ORDERED);
        newOrder.setDeliveryNote(order.getDeliveryNote());
        newOrder.setRestaurantNote(order.getRestaurantNote());
        newOrder.setPhone(order.getPhone());
        //set address

        return orderRepo.save(newOrder);

    }

    public Order getOrderByRefId(String refid,Customer customer){
        String[] refidSplit = refid.split("\\$");
        Optional<Order> orderOpt =
                orderRepo.findByOrderReferenceAndIdAndCustomer(refidSplit[0]+"$",Long.parseLong(refidSplit[1]), customer);
        if(orderOpt.isPresent()){
            return orderOpt.get();
        }
        return null;
    }

    public Order acceptOrder(String refid, RestaurantOwner owner){

        System.out.println(refid + " ******************");
        String[] refidSplit = refid.split("\\$");
        Arrays.stream(refidSplit).forEach(System.out::println);
        Optional<Order> orderOpt =
                orderRepo.findByOrderReferenceAndIdAndIsAcceptedAndRestaurantOwners(refidSplit[0]+"$",Long.parseLong(refidSplit[1]),false,owner);

        if(orderOpt.isPresent()){
            Order order = orderOpt.get();
            order.setAccepted(true);
            order.setStatus(Status.ACCEPTED);
            return orderRepo.save(order);
        }

        return null;

    }

    public Order declineOrder(String refid, RestaurantOwner owner){

        System.out.println(refid + " ******************");
        String[] refidSplit = refid.split("\\$");
        Arrays.stream(refidSplit).forEach(System.out::println);
        Optional<Order> orderOpt =
                orderRepo.findByOrderReferenceAndIdAndRestaurantOwners(refidSplit[0]+"$",Long.parseLong(refidSplit[1]),owner);

        if(orderOpt.isPresent()){
            Order order = orderOpt.get();
            order.setDeclined(true);
            order.setAccepted(false);
            order.setStatus(Status.DECLINED);
            return orderRepo.save(order);
        }

        return null;

    }

    public Order deliverOrder(String refid, RestaurantOwner owner) {

        System.out.println(refid + " ******************");
        String[] refidSplit = refid.split("\\$");
        Arrays.stream(refidSplit).forEach(System.out::println);
        Optional<Order> orderOpt =
                orderRepo.findByOrderReferenceAndIdAndIsAcceptedAndRestaurantOwners(refidSplit[0]+"$",Long.parseLong(refidSplit[1]),true,owner);

        if(orderOpt.isPresent()){
            Order order = orderOpt.get();
            order.setStatus(Status.DELIVERED);
            return orderRepo.save(order);
        }

        return null;

    }


}
