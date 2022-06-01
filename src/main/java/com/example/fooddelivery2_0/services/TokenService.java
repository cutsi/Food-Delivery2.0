package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.Token;
import com.example.fooddelivery2_0.entities.User;
import com.example.fooddelivery2_0.repos.TokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenRepo tokenRepo;

    public Optional<Token> getTokenByUser(User user){
        return tokenRepo.findTokenByUser(user);
    }
    public Optional<Token> getTokenByCode(String code){
        return tokenRepo.findTokenByToken(code);
    }
    public void deleteToken(Token token){
        tokenRepo.delete(token);
    }
    public void saveToken(Token token){
        tokenRepo.save(token);
    }
    public boolean isTokenExpired(Optional<Token> token){
        if(!token.isPresent()){
            return true;
        }
        if(LocalDateTime.now(ZoneId.of("CET")).isAfter(token.get().getExpiresAt())){
            return true;
        }
        return false;
    }

    private List<Token> getAllTokensFromUser(User user) {
        return tokenRepo.findAllByUser(user);
    }
    public void deleteAllExpiredTokensFromUser(User user){
        List<Token> userTokens = getAllTokensFromUser(user);
        for (Token token:userTokens) {
            if(LocalDateTime.now(ZoneId.of("CET")).isAfter(token.getExpiresAt()))
                tokenRepo.delete(token);
        }
    }
    public boolean checkForNonExpiredTokens(User user){
        deleteAllExpiredTokensFromUser(user);
        if(getAllTokensFromUser(user).size() > 0)
            return true;
        return false;
    }
}
