package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.Rating;
import com.example.fooddelivery2_0.entities.Response;
import com.example.fooddelivery2_0.entities.Restaurant;
import com.example.fooddelivery2_0.entities.User;
import com.example.fooddelivery2_0.repos.RatingRepo;
import javassist.expr.NewArray;
import lombok.AllArgsConstructor;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RatingService {
    private final RatingRepo ratingRepo;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public List<Rating> getAllByUser(User user){
        return ratingRepo.findAllByCustomer(user);
    }
    public Optional<Rating> getById(Long id){
        return ratingRepo.findById(id);
    }
    public List<Rating> getAllByCreatedAt(LocalDateTime createdAt){
        return ratingRepo.findAllByCreatedAt(createdAt);
    }
    public List<Rating> getAllByCreatedAfter(LocalDateTime createdAt){
        return ratingRepo.findAllByCreatedAtAfter(createdAt);
    }
    public List<Rating> getAllByCreatedBefore(LocalDateTime createdAt){
        return ratingRepo.findAllByCreatedAtBefore(createdAt);
    }
    public List<Rating> getAllByIsApproved(){
        return ratingRepo.findAllByIsApprovedTrue();
    }
    public List<Rating> getAll(){
        return ratingRepo.findAll();
    }
    public List<Rating> getAllByResponseId(Long id){
        return ratingRepo.findAllByResponseId(id);
    }
    public List<Rating> getAllByRestaurant(Restaurant restaurant){
        return ratingRepo.findAllByRestaurant(restaurant);
    }
    public List<Rating> getAllApprovedRatingsByRestaurant(Restaurant restaurant){
        return ratingRepo.findAllByRestaurantAndIsApprovedTrue(restaurant);
    }
    public List<Rating> getAllByIsApprovedFalse(){
        return ratingRepo.findAllByIsApprovedFalse();
    }
    public void delete(Rating rating){
        ratingRepo.delete(rating);
    }
    public String getAverageRating(Restaurant restaurant){
        List<Rating> ratings = ratingRepo.findAllByRestaurant(restaurant);
        double avgRating = 0.00;
        for (Rating rating:ratings) {
            avgRating = avgRating + Double.valueOf(rating.getRating());
            System.out.println("RATING: " + rating.getRating());
        }
        System.out.println(avgRating + "/" + (double) ratings.size());
        return df.format(avgRating/ (double) ratings.size());
        //return avgRating/Double.valueOf(comments.size());
    }

    public List<Rating> getApprovedRatingsWithNoResponse(){
        return ratingRepo.findAllByResponseIsNullAndIsApprovedTrue();
    }


    public void save(Rating rating){
        ratingRepo.save(rating);
    }

    public List<Rating> getRatingsInTheLastThreeDays(){
        LocalTime midnight = LocalTime.of(00, 00, 00, 000000 );
        return ratingRepo.findRatingsBetweenTwoDates(
                LocalDateTime.of(LocalDate.now().minusDays(3), midnight),
                LocalDateTime.now());
    }

    public List<Rating> getRatingsWithNoResponseInTheLastThreeDays(){
        LocalTime midnight = LocalTime.of(00, 00, 00, 000000 );
        return ratingRepo.findRatingsBetweenTwoDatesWithNoResponse(
                LocalDateTime.of(LocalDate.now().minusDays(3), midnight),
                LocalDateTime.now());
    }

    public List<Rating> getAllReportedComments(){
        return ratingRepo.findAllByIsReportedTrue();
    }
}
