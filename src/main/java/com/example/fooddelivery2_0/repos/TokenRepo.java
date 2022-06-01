package com.example.fooddelivery2_0.repos;

import com.example.fooddelivery2_0.entities.Token;
import com.example.fooddelivery2_0.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TokenRepo extends JpaRepository<Token, Long> {
    Optional<Token> findTokenByUser(User user);
    Optional<Token> findTokenByToken(String token);
    Optional<Token> findTokenByInfo(String info);
    List<Token> findAllByUser(User user);
}
