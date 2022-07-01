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
            value = "select sum(CAST(price AS FLOAT)) from orders where restaurant_id = ?1")
    Double findAllIncome(Long restaurant_id);

    @Query(nativeQuery = true,
            value = "select sum(CAST(price AS FLOAT)) from orders where extract(day from created_at) = ?1 and extract(month from created_at) = ?2 and extract(year from created_at) = ?3 and restaurant_id = ?4")
    Double findIncomeByDate(Integer day, Integer month, Integer year, Long restaurant_id);

    @Query(nativeQuery = true,
            value = "select sum(CAST(price AS FLOAT)) from orders where extract(month from created_at) = ?1 and extract(year from created_at) = ?2 and restaurant_id = ?3")
    Double findIncomeByMonthAndYear(Integer month, Integer year, Long restaurant_id);

    @Query(nativeQuery = true,
            value = "select sum(CAST(price AS FLOAT)) from orders where extract(year from created_at) = ?1 and restaurant_id = ?2")
    Double findIncomeByYear(Integer lastYear, Long restaurant_id);

    @Query(nativeQuery = true,
            value = "SELECT customer_id FROM orders GROUP BY customer_id;")
    List<Integer> findCustomersCountList();



    @Query(nativeQuery = true,
            value = "select * from orders where created_at between ?1 and ?2")
    List<Order> findOrdersBetweenTwoDates(LocalDateTime startDate, LocalDateTime endDate);

    @Query(nativeQuery = true,
            value = "select  COUNT(*) from orders where extract(month from created_at) = ?1 and extract(year from created_at) = ?2 and restaurant_id = ?3")
    Integer findOrdersCountByMonthAndYear(Integer month, Integer year, Long restaurant_id);

    @Query(nativeQuery = true,
            value = "select COUNT(*) from orders where extract(day from created_at) = ?1 and extract(month from created_at) = ?2 and extract(year from created_at) = ?3 and restaurant_id = ?4")
    Integer findOrdersCountByDate(Integer day, Integer month, Integer year, Long restaurant_id);

    @Query(nativeQuery = true,
            value = "select COUNT(*) from orders where restaurant_id = ?1")
    Integer findAllOrdersCount(Long restaurant_id);

    @Query(nativeQuery = true,
            value = "SELECT customer_id FROM orders where restaurant_id = ?1 GROUP BY customer_id ORDER BY COUNT(customer_id) DESC LIMIT 3")
    List<Long> findTopThreeCustomersIds(Long restaurant_id);

    @Query(nativeQuery = true,
            value = "SELECT name FROM users where id in ?1")
    List<String> findTopThreeCustomers(List<Long> customerIds);

    @Query(nativeQuery = true,
            value = "SELECT order_contents.name FROM orders JOIN order_contents ON orders.id = order_contents.order_id WHERE restaurant_id= ?1 GROUP BY order_contents.name ORDER BY COUNT(order_contents.name) DESC LIMIT 3;")
    List<String> findTopThreeMeals(Long restaurant_id);
}

