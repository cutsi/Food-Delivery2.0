package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.Order;
import com.example.fooddelivery2_0.entities.Rating;
import com.example.fooddelivery2_0.entities.Restaurant;
import com.example.fooddelivery2_0.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    List<Rating> findAllByIsApprovedFalse();
    List<Rating> findAllByResponseIsNullAndIsApprovedTrue();
    List<Rating> findAllByRestaurantAndIsApprovedTrue(Restaurant restaurant);
    List<Rating> findAllByIsReportedTrue();
    @Query(nativeQuery = true,
            value = "select * from ratings where created_at between ?1 and ?2")
    List<Rating> findRatingsBetweenTwoDates(LocalDateTime startDate, LocalDateTime endDate);

    @Query(nativeQuery = true,
            value = "select * from ratings where created_at between ?1 and ?2 and response_id=null")
    List<Rating> findRatingsBetweenTwoDatesWithNoResponse(LocalDateTime startDate, LocalDateTime endDate);
}
