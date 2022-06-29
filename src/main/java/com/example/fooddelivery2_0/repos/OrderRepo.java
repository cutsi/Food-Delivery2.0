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

    @Query(nativeQuery = true, value = "select * from orders where (status = ?1 or status = ?2) and restaurant_id = ?3")
    List<Order> findByStatusOrStatusAndRestaurant(Status ordered, Status accepted, Long id);
    List<Order> findAllByStatusOrStatusAndCustomer(Status ordered, Status accepted, Customer customer);
    List<Order> findAllByStatusAndRestaurant(Status status, Restaurant restaurant);
    @Query(nativeQuery = true,
    value = "select * from orders where (status = ?1 or status = ?2) and restaurant_id = ?3")
    List<Order> findNotDelivered(String ordered, String accepted, Long id);
    //List<Order> findByStatusOrStatus
    @Query(nativeQuery = true,
    value = "select * from orders where created_at>?1 and created_at<?2 and restaurant_id=?3")
    List<Order> findAllByCreatedAtAndRestaurant(LocalDateTime dayStart, LocalDateTime dayFinish, Long restaurant);



    @Query(nativeQuery = true,
            value = "select * from orders where created_at between ?1 and ?2")
    List<Order> findOrdersCountBetweenTwoDates(LocalDateTime startDate, LocalDateTime endDate);

    @Query(nativeQuery = true,
            value = "select sum(CAST(price AS FLOAT)) from orders")
    Double findAllIncome();

    @Query(nativeQuery = true,
            value = "select sum(CAST(price AS FLOAT)) from orders where extract(day from created_at) = ?1 and extract(month from created_at) = ?2 and extract(year from created_at) = ?3")
    Double findIncomeByDate(Integer day, Integer month, Integer year);

    @Query(nativeQuery = true,
            value = "select sum(CAST(price AS FLOAT)) from orders where extract(month from created_at) = ?1 and extract(year from created_at) = ?2")
    Double findIncomeByMonthAndYear(Integer month, Integer year);

    @Query(nativeQuery = true,
            value = "select sum(CAST(price AS FLOAT)) from orders where extract(year from created_at) = ?1")
    Double findIncomeByYear(Integer lastYear);

    @Query(nativeQuery = true,
            value = "SELECT customer_id FROM orders GROUP BY customer_id;")
    List<Integer> findCustomersCountList();
}

