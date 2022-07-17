package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.Customer;
import com.example.fooddelivery2_0.entities.Token;
import com.example.fooddelivery2_0.entities.User;
import com.example.fooddelivery2_0.repos.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final TokenService tokenService;
    EmailService emailService;//FIX
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final String USER_NOT_FOUND = "Korisnik sa emailom: %s nije pronađen";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepo.findByEmail(email);
        if(!user.isPresent()) throw new UsernameNotFoundException(String.format(USER_NOT_FOUND, email));
        return user.get();
    }
    public void signUpUser(User user) throws UnsupportedEncodingException, MessagingException {
        var userExists = userRepo.findByEmail(user.getEmail()).isPresent();
        //String verificationCode = RandomString.make(64);

        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.
            //user.setVerificationCode(verificationCode);
            if(!tokenService.checkForNonExpiredTokens(user))
                emailService.sendRegistrationConfirmEmail(user);
            throw new IllegalStateException("Email je zauzet");
        }
        var encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
        //Token verificationCode = new Token(userService.getUserByEmail(email).get());

        var token = new Token(user);
        tokenService.saveToken(token);
        emailService.sendRegistrationConfirmEmail(user);
        // TODO: Send confirmation token later
    }
    public Optional<User> getUserByEmail(String email){
        return userRepo.findByEmail(email);
    }
    public Optional<User> getUserByName(String name){
        return userRepo.findByName(name);
    }
    public Optional<User> getUserById(Long id){
        return userRepo.findById(id);
    }
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
    public Optional<User> getUserByPhone(String phone){
        return userRepo.findByPhone(phone);
    }
    public Optional<User> getCurrentUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String username;
        if(authentication.getPrincipal() instanceof UserDetails)
            username = ((UserDetails)authentication.getPrincipal()).getUsername();
        else
            username = authentication.getPrincipal().toString();
        var user = userRepo.findByEmail(username);
        return user;
    }
    public void saveUser(User user){
        userRepo.save(user);
    }

    public Page<User> findPage(int pageNumber){
        var pageable = PageRequest.of(pageNumber - 1,5);
        return userRepo.findAll(pageable);
    }

    public Page<Customer> getCustomersByIdNameEmail(int pageNumber, String keyword){
        var pageable = PageRequest.of(pageNumber - 1,5);
        return userRepo.findUsersByIdNameEmail(pageable, keyword);
    }

    public List<Customer> getNewCustomers() {
        var startDay = LocalTime.of(00, 00, 00, 000000 );
        return userRepo.findUsersBetweenTwoDates(LocalDateTime.of(LocalDate.now(), startDay),
                LocalDateTime.now());
    }
    public Integer getNumberOfCustomersThisMonth(){
        var startMonth = LocalDate.now().withDayOfMonth(1);
        var midnight = LocalTime.of(00, 00, 00, 000000 );
        return userRepo.findNumberOfUsersBetweenDates(LocalDateTime.of(startMonth, midnight), LocalDateTime.now());
    }
    public Integer getNumberOfCustomersToday(){
        var midnight = LocalTime.of(00, 00, 00, 000000 );
        return userRepo.findNumberOfUsersBetweenDates(LocalDateTime.of(LocalDate.now(), midnight), LocalDateTime.now());
    }
    public Integer getNumberOfAllCustomers(){
        return userRepo.findNumberOfAllUsers();
    }

}
