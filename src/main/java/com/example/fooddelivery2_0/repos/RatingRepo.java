package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.Rating;
import com.example.fooddelivery2_0.entities.Restaurant;
import com.example.fooddelivery2_0.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public interface RatingRepo extends JpaRepository<Rating, Long> {
    List<Rating> findAllByCustomer(User user);
    List<Rating> findAllByCreatedAt(LocalDateTime createdAt);
    List<Rating> findAllByCreatedAtAfter(LocalDateTime createdAt);
    List<Rating> findAllByCreatedAtBefore(LocalDateTime createdAt);
    List<Rating> findAllByIsApprovedTrue();
    List<Rating> findAllByResponseId(Long responseId);
    List<Rating> findAllByRestaurant(Restaurant restaurant);
    List<Rating>findAllByIsApprovedFalse();
    List<Rating> findAllByResponseIsNull();
}
