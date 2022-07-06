package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.Customer;
import com.example.fooddelivery2_0.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    //Optional<User> findByAddress(String address);
    //@Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    //User findByVerificationCode(String code);
    //Optional<User> findByRestaurant(Restaurant restaurant);

}
