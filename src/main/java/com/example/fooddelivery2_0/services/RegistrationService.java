package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.Utils.EmailValidator;
import com.example.fooddelivery2_0.Utils.Requests.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final UserService userService;
    private final EmailValidator emailValidator;

    public void register(RegistrationRequest request, String siteURL, String redirect) throws MessagingException, UnsupportedEncodingException {
        boolean isValidEmail = emailValidator
                .test(request.getEmail());
        if(isValidEmail){
            throw new IllegalStateException("Email not valid");
        }
        /*userService.signUpUser(
                new User(request.getEmail(),
                        request.getPhone(),
                        request.getPassword(),
                        request.getName()),
                siteURL,
                redirect);*/
    }


}
