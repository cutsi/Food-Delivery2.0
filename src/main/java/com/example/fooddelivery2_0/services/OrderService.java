package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.Utils.CharJsDataWrapper;
import com.example.fooddelivery2_0.Utils.Status;
import com.example.fooddelivery2_0.entities.*;
import com.example.fooddelivery2_0.repos.OrderRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;

    public String[] serialize(String[] foodItems){

        String orderStr = new String();
        String fullOrderStr = new String();
        String[] fullOrderArr = new String[foodItems.length];
        for (int i = 0; i< foodItems.length-1;i++) {
            for(int j = foodItems[i].indexOf("orderDetail")+14; j<foodItems[i].length()-1;j++){
                orderStr += foodItems[i].charAt(j);
            }
            for(int k = 0;  k<foodItems[i].indexOf("orderDetail")-1;k++){
                fullOrderStr += foodItems[i].charAt(k);
            }
            System.out.println("FULLORDERSTR: " + fullOrderStr);
            System.out.println("ORDERSTR: " + orderStr);
            fullOrderArr[i] = fullOrderStr + orderStr;
        }
        return fullOrderArr;
    }
    public List<Order> getAllOrders(){
        return orderRepo.findAll();
    }
    public List<Order> getAllOrdersByCustomer(Customer customer){
        return orderRepo.findAllByCustomer(customer);
    }
    public List<Order> getAllOrdersByCustomerOrderByCreatedAtDesc(Customer customer){
        return orderRepo.findAllByCustomerOrderByCreatedAtDesc(customer);
    }
    public List<Order> getAllByRestaurant(Restaurant restaurant){
        return orderRepo.findAllByRestaurant(restaurant);
    }
    public List<Order> getAllByRestaurantOrderByCreatedAtDesc(Restaurant restaurant){
        return orderRepo.findAllByRestaurantOrderByCreatedAtDesc(restaurant);
    }

    public void save(Order order) {
        orderRepo.save(order);
    }




    public List<Integer> getCustomersCountList() {
        return orderRepo.findCustomersCountList();
    }

    public Map<String, Object> getOrdersStats(Restaurant restaurant){

        List<Order> orders = orderRepo.findAllByRestaurant(restaurant);
        Map<String, Object> ordersStats = new HashMap<>();

        LocalDateTime today = LocalDateTime.now();

        ordersStats.put("allOrdersCount", orderRepo.findAllOrdersCount(restaurant.getId()));
        ordersStats.put("pendingOrdersCount", orderRepo.findAllByStatusAndRestaurant(Status.ORDERED, restaurant).size() + orderRepo.findAllByStatusAndRestaurant(Status.ACCEPTED, restaurant).size());
        ordersStats.put("declinedOrdersCount", orderRepo.findAllByStatusAndRestaurant(Status.DECLINED, restaurant).size());
        ordersStats.put("deliveredOrdersCount", orderRepo.findAllByStatusAndRestaurant(Status.DELIVERED, restaurant).size());


        Integer tempOrderCount = orderRepo.findOrdersCountByDate(today.getDayOfMonth(), today.getMonthValue(), today.getYear(), restaurant.getId());
        ordersStats.put("todayOrdersCount", tempOrderCount == null ? 0 : tempOrderCount);

        tempOrderCount = orderRepo.findOrdersCountByDate(today.getDayOfMonth(), today.getMonthValue(), today.minusYears(1).getYear(), restaurant.getId());
        ordersStats.put("todayLastYearOrdersCount", tempOrderCount == null ? 0 : tempOrderCount);

        tempOrderCount = orderRepo.findOrdersCountByMonthAndYear(today.getMonthValue(),today.getYear(), restaurant.getId());
        ordersStats.put("monthOrdersCount", tempOrderCount == null ? 0 : tempOrderCount);

        tempOrderCount = orderRepo.findOrdersCountByMonthAndYear(today.getMonthValue(),today.minusYears(1).getYear(), restaurant.getId());
        ordersStats.put("monthLastYearOrdersCount", tempOrderCount == null ? 0 : tempOrderCount);


        Double tempIncome = orderRepo.findAllIncome(restaurant.getId());
        ordersStats.put("totalIncome", tempIncome == null ? 0 : tempIncome);

        tempIncome = orderRepo.findIncomeByDate(today.getDayOfMonth(), today.getMonthValue(), today.getYear(), restaurant.getId());
        ordersStats.put("todayIncome", tempIncome == null ? 0 : tempIncome);

        tempIncome = orderRepo.findIncomeByMonthAndYear(today.getMonthValue(),today.getYear(), restaurant.getId());
        ordersStats.put("monthIncome", tempIncome == null ? 0 : tempIncome);

        tempIncome = orderRepo.findIncomeByYear(today.minusYears(1).getYear(), restaurant.getId());
        ordersStats.put("lastYearIncome", tempIncome == null ? 0 : tempIncome);

        tempIncome = orderRepo.findIncomeByDate(today.getDayOfMonth(), today.getMonthValue(), today.minusYears(1).getYear(), restaurant.getId());
        ordersStats.put("todayLastYearIncome", tempIncome == null ? 0 : tempIncome);

        tempIncome = orderRepo.findIncomeByMonthAndYear(today.getMonthValue(), today.minusYears(1).getYear(), restaurant.getId());
        ordersStats.put("monthLastYearIncome", tempIncome == null ? 0 : tempIncome);

        LocalDateTime todayMinusSeven = today.minusDays(7);
        List<Order> lastSevenDaysOrders = orderRepo.findOrdersBetweenTwoDates(todayMinusSeven, today);

        List<CharJsDataWrapper> lastSevenDaysOrdersWrapped = new ArrayList<>();
        List<CharJsDataWrapper> lastSevenDaysIncomeWrapped = new ArrayList<>();

        for(int i =0; i<7; i++){

            LocalDate finalToday = today.toLocalDate();

            List<Order> lastSevenDaysFilteredOrders = lastSevenDaysOrders.stream().filter(o->
                    o.getCreatedAt().toLocalDate().equals(finalToday)
            ).collect(Collectors.toList());

            lastSevenDaysOrdersWrapped.add(new CharJsDataWrapper<>(finalToday.toString(), lastSevenDaysFilteredOrders.size()));

            Double total = lastSevenDaysFilteredOrders.stream()
                    .mapToDouble(o->
                            Double.parseDouble(o.getPrice())
                    ).sum();
            lastSevenDaysIncomeWrapped.add(new CharJsDataWrapper<>(finalToday.toString(), total));

            today = today.minusDays(1);

        }

        ordersStats.put("lastSevenDaysOrders", lastSevenDaysOrdersWrapped);
        ordersStats.put("lastSevenDaysIncome", lastSevenDaysIncomeWrapped);

        ordersStats.put("topThreeCustomers", orderRepo.findTopThreeCustomers(orderRepo.findTopThreeCustomersIds(restaurant.getId())));
        ordersStats.put("topThreeMeals",orderRepo.findTopThreeMeals(restaurant.getId()));

        return  ordersStats;
    }

    public List<Long> getTopHundredCustomers(){
        return orderRepo.findTopHundredCustomersIds();
    }
    public Integer getNumberOfOrdersByCustomer(Long id){
        return orderRepo.findNumberOfOrdersByCustomer(id);
    }

    private LocalDate getFirstDayOfTheMonth(){
        return LocalDate.now().withDayOfMonth(1);

    }
    private LocalTime getMidnight(){
        return LocalTime.of(00, 00, 00, 000000 );
    }
    public Float getProfitThisMonth(){
        LocalDate startMonth = LocalDate.now().withDayOfMonth(1);
        LocalTime midnight = LocalTime.of(00, 00, 00, 000000 );
        return orderRepo.findProfitBetweenDates(LocalDateTime.of(startMonth, midnight), LocalDateTime.now());
    }
    public Float getProfitToday(){
        LocalTime midnight = LocalTime.of(00, 00, 00, 000000 );
        return orderRepo.findProfitBetweenDates(LocalDateTime.of(LocalDate.now(), midnight), LocalDateTime.now());
    }

    public Integer getNumberOfOrdersToday(){
        LocalTime midnight = LocalTime.of(00, 00, 00, 000000 );
        return orderRepo.findNumberOfOrdersBetweenDates(LocalDateTime.of(LocalDate.now(), midnight), LocalDateTime.now());
    }

    public Integer getNumberOfOrdersThisMonth(){
        LocalDate startMonth = LocalDate.now().withDayOfMonth(1);
        LocalTime midnight = LocalTime.of(00, 00, 00, 000000 );
        return orderRepo.findNumberOfOrdersBetweenDates(LocalDateTime.of(startMonth, midnight), LocalDateTime.now());
    }
    public Integer getNumberOfAllOrders(){
        return orderRepo.findNumberOfAllOrders();
    }

    public float getAdminTotalProfits(){
        float[] profitList = orderRepo.findSumOfAllRestaurantOrdersWithAdminsPercentage();
        float total = 0;
        for(int i = 0; i < profitList.length; i++){
            total += profitList[i];
        }
        return total;
    }
    public float getAdminProfitsThisMonth(){
        LocalDate startMonth = getFirstDayOfTheMonth();
        LocalTime midnight = getMidnight();

        float[] profitList = orderRepo.findSumOfAllRestaurantOrdersWithAdminsPercentageBetweenDates(LocalDateTime.of(startMonth, midnight), LocalDateTime.now());
        float total = 0;
        for(int i = 0; i < profitList.length; i++){
            total += profitList[i];
        }
        return total;
    }

    public float getAdminProfitsToday(){
        float[] profitList = orderRepo.findSumOfAllRestaurantOrdersWithAdminsPercentageBetweenDates
                (LocalDateTime.of(LocalDate.now(), getMidnight()), LocalDateTime.now());
        float total = 0;
        for(int i = 0; i < profitList.length; i++){
            total += profitList[i];
        }
        return total;
    }

    public Page<Order> findPage(int pageNumber, Long id){
        Pageable pageable = PageRequest.of(pageNumber - 1,5);

        for (Order order:orderRepo.findOrdersByCustomer(pageable, id)) {
            System.out.println("PAGED ORDER: " + order.getRestaurant().getName());
        }
        return orderRepo.findOrdersByCustomer(pageable, id);
    }
}
