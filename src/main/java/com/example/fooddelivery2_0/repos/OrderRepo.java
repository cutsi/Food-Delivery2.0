package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.Customer;
import com.example.fooddelivery2_0.entities.Order;
import com.example.fooddelivery2_0.entities.Restaurant;
import com.example.fooddelivery2_0.entities.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderReferenceAndIdAndIsAcceptedAndRestaurantOwners(String ref, Long id, boolean accepted, RestaurantOwner owner);
    Optional<Order> findByOrderReferenceAndIdAndRestaurantOwners(String ref, Long id, RestaurantOwner owner);
    Optional<Order> findByOrderReferenceAndIdAndCustomer(String ref, Long id, Customer customer);

    @Override
    List<Order> findAll();
    List<Order> findAllByRestaurant(Restaurant restaurant);
    List<Order> findAllByCustomer(Customer customer);
    List<Order> findAllByCustomerOrderByCreatedAtDesc(Customer customer);
    List<Order> findAllByRestaurantOrderByCreatedAtDesc(Restaurant restaurant);

}
