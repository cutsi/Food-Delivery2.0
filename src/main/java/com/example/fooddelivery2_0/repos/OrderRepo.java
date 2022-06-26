package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.Utils.Status;
import com.example.fooddelivery2_0.entities.Customer;
import com.example.fooddelivery2_0.entities.Order;
import com.example.fooddelivery2_0.entities.Restaurant;
import com.example.fooddelivery2_0.entities.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
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

    List<Order> findByStatusOrStatus(Status ordered, Status accepted);
    List<Order> findAllByStatusOrStatusAndCustomer(Status ordered, Status accepted, Customer customer);

    @Query(nativeQuery = true,
    value = "select * from orders where (status = ?1 or status = ?2) and restaurant_id = ?3")
    List<Order> findNotDelivered(String ordered, String accepted, Long id);
    //List<Order> findByStatusOrStatus
    @Query(nativeQuery = true,
    value = "select * from orders where created_at>?1 and created_at<?2 and restaurant_id=?3")
    List<Order> findAllByCreatedAtAndRestaurant(LocalDateTime dayStart, LocalDateTime dayFinish, Long restaurant);
}
