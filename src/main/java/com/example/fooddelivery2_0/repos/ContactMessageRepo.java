package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ContactMessageRepo extends JpaRepository<ContactMessage, Long> {
    @Override
    Optional<ContactMessage> findById(Long aLong);
    List<ContactMessage> findAllByEmail(String email);
    List<ContactMessage> findAllByMessage(String message);
    List<ContactMessage> findAllByEmailAndMessage(String email, String message);


}
