package com.example.fooddelivery2_0.Controllers;
import com.example.fooddelivery2_0.Utils.Requests.RegistrationRequest;
import com.example.fooddelivery2_0.Utils.UserRole;
import com.example.fooddelivery2_0.entities.Customer;
import com.example.fooddelivery2_0.services.EmailService;
import com.example.fooddelivery2_0.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping(path = "prijava")
@AllArgsConstructor
public class RegistrationController {
    private final EmailService emailService;
    private final UserService userService;
    private final String VERIFY_LINK = "/verify";
    private final String REGISTRATION_FAIL_MESSAGE = "Vaš račun je već registriran";
    private final String REGISTRATION_SUCCESS_MESSAGE = "Vaš račun je uspješno kreiran. Pregledajte svoj mail i verificirajte račun. Nakon toga se možete ulogirati.";
    private final String VERIFY_SUCCESS_MESSAGE = "Uspješno ste se verificirali. Vaš račun je sada osposobljen za naručivanje. Dobar tek!";
    private final String VERIFY_FAIL_MESSAGE = "Nismo vam mogli verificirati račun. Vaš račun je već verificiran ili je verifikacijski kod netočan.";

    @GetMapping(path = "")
    public String getSignup(@ModelAttribute RegistrationRequest registrationRequest, Model model){
        model.addAttribute("user", new Customer());
        return "signup";
    }
    @PostMapping(path = "")
    public String postSignUp(@ModelAttribute RegistrationRequest request, Model model, HttpServletRequest siteURL,
                             @RequestParam("password1") String pass) throws MessagingException, UnsupportedEncodingException {
        request.setPassword(pass);
        if (userService.getUserByEmail(request.getEmail()).isPresent()){
            model.addAttribute("message", REGISTRATION_FAIL_MESSAGE);
            return "fail";
        }
        var customer = new Customer
                (request.getName(), request.getEmail(), request.getPhone(),
                        request.getPassword());
        customer.setUserRole(UserRole.CUSTOMER);
        userService.signUpUser(customer);
        model.addAttribute("message", REGISTRATION_SUCCESS_MESSAGE);
        return "success";
    }
    @GetMapping("verifikacija")
    public String verifyUser(@Param("code") String code, Model model) {
        if (emailService.verify(code)){
            model.addAttribute("message", VERIFY_SUCCESS_MESSAGE);
            return "success";
        }
        model.addAttribute("message", VERIFY_FAIL_MESSAGE);
        return "fail";
    }
}

