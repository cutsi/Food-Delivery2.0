package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.Customer;
import com.example.fooddelivery2_0.repos.CustomerRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;
    private final OrderService orderService;
    private final UserService userService;
    public void save(Customer customer){
        customerRepo.save(customer);
    }

    public Map<Customer, Integer> getTopOneHundredCustomersWithNumberOfOrders(){
        Map<Customer, Integer> customersNumberOfOrders = new HashMap<>();
        for (Long id: orderService.getTopHundredCustomers()) {
            customersNumberOfOrders.put((Customer) userService.getUserById(Long.valueOf(id)).get(),
                    orderService.getNumberOfOrdersByCustomer(Long.valueOf(id)));
        }
        return customersNumberOfOrders;
    }

}
