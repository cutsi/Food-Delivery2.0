package com.example.fooddelivery2_0.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/admin")
@AllArgsConstructor
public class AdminController {
    @GetMapping
    public String getAdmin(){
        return "admin";
    }
}
