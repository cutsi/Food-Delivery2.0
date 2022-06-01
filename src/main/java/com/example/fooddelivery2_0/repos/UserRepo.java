package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
@Repository
@Transactional
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String firstName);
    Optional<User> findByPhone(String phone);

    //Optional<User> findByAddress(String address);
    //@Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    //User findByVerificationCode(String code);
    //Optional<User> findByRestaurant(Restaurant restaurant);

}
