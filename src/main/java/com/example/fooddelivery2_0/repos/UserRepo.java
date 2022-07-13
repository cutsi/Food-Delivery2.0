package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.Customer;
import com.example.fooddelivery2_0.entities.FoodItem;
import com.example.fooddelivery2_0.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
@Transactional
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String firstName);
    Optional<User> findByPhone(String phone);
    List<User> findAll();
    @Query(value = "select * from users where created_at between ?1 and ?2 and user_role='CUSTOMER'",
            nativeQuery = true)
    List<Customer> findUsersBetweenTwoDates(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT * FROM users f WHERE f.name ILIKE CONCAT('%',:name,'%') or f.email ILIKE CONCAT('%',:name,'%')"
            , nativeQuery = true)
    Page<Customer> findUsersByIdNameEmail(Pageable pageable, @Param("name") String name);//cast name to bigint for id search to work

    @Query(nativeQuery = true,
            value = "select count(users) from users where created_at between ?1 and ?2")
    Integer findNumberOfUsersBetweenDates(LocalDateTime startDay, LocalDateTime endDay);

    @Query(nativeQuery = true,
            value = "select count(users) from users where user_role='CUSTOMER'")
    Integer findNumberOfAllUsers();
    //Optional<User> findByAddress(String address);
    //@Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    //User findByVerificationCode(String code);
    //Optional<User> findByRestaurant(Restaurant restaurant);

}
