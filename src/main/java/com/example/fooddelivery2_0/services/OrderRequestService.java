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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class OrderRequestService {
    private OrderRepo orderRepo;
    private RestaurantRepo restaurantRepo;
    private OrderContentRepo orderContentRepo;
    private RestaurantService restaurantService;
    //private OrderContentDetailsRepo orderContentDetailRepo;
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
                .mapToDouble(cartItem -> Double.parseDouble(cartItem.getPrice()))//CHANGE
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
            Map<OrderContent, String> orderAndPrice = new HashMap<>();

        });
        return cartItems;
    }
    private String getFoodItemCondimentPrice(OrderContent orderContent){
        return null;
    }
    @Transient
    public Order saveNewOrder(OrderRequest order, Customer customer, Long restaurantId) throws NoSuchAlgorithmException {

        /*order.getFoodItems().stream().forEach(p->{
            orderContentDetailRepo.save(p.getOrderDetail());//TO CHANGE
        });*/
        Order newOrder = new Order(ReferenceGenerator.generateReference(), customer, restaurantRepo.getById(restaurantId), null,  Status.ORDERED);
        newOrder.setDeliveryNote(order.getDeliveryNote());
        newOrder.setRestaurantNote(order.getRestaurantNote());
        newOrder.setPhone(order.getPhone());
        orderRepo.save(newOrder);

        List<OrderContent> content = order.getFoodItems();
        content.forEach(f-> f.setOrder(newOrder));
        content = orderContentRepo.saveAll(content);

        newOrder.setContents(content);
        return orderRepo.save(newOrder);

        //set address
    }

    public Order getOrderByRefId(String refid,Customer customer){
        String[] refidSplit = refid.split("\\$");
        System.out.println("REFID SPLIT:" + refidSplit);
        System.out.println("refidSplit[0]:" + refidSplit[0]);
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
        String orderId = refidSplit[1];
        Arrays.stream(refidSplit).forEach(System.out::println);
        Optional<Order> orderOpt =
                orderRepo.findByOrderReferenceAndIdAndRestaurantOwners(refidSplit[0]+"$",Long.parseLong(orderId),owner);

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


    public List<Order> getNotDeliveredOrder(Long restaurant) {//GETS ALL ACTIVE ORDERS NOT JUST RESTAURANT ONES
        return orderRepo.findNotDelivered(Status.ORDERED.name(), Status.ACCEPTED.name(), restaurant);
    }
    public List<Order> getActiveOrdersByCustomer(Customer customer){
        return orderRepo.findAllByStatusOrStatusAndCustomer(Status.ORDERED, Status.ACCEPTED, customer);
    }

    public Order getById(Long id) {
        return orderRepo.findById(id).get();
    }

    private LocalDateTime getEndOfDay(){
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.of(23, 59, 59, 999999 );
        return LocalDateTime.of(currentDate, currentTime);
    }

    private LocalDateTime getStartOfDay(){
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.of(0, 0, 0, 0 );
        return LocalDateTime.of(currentDate, currentTime);
    }
    public List<Order> getAllOrdersByCreatedAtTodayAndRestaurant(Long restaurantId){
         return orderRepo.findAllByCreatedAtAndRestaurant(getStartOfDay(), getEndOfDay(), restaurantId);
    }


    public Map<Restaurant, Integer> getRestaurantsAndOrdersByRestaurantToday(){
        Map<Restaurant,Integer> numberOfOrdersPerRestaurantToday = new HashMap<>();
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        for (Restaurant restaurant: restaurants) {
            numberOfOrdersPerRestaurantToday.put(restaurant, getAllOrdersByCreatedAtTodayAndRestaurant(restaurant.getId()).size());
        }
        return numberOfOrdersPerRestaurantToday;
    }


}
