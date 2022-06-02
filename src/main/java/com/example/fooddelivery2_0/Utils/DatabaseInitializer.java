package com.example.fooddelivery2_0.Utils;
import com.example.fooddelivery2_0.entities.*;
import com.example.fooddelivery2_0.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class DatabaseInitializer {

    private final CategoryRepo categoryRepo;
    private final PortionNameRepo portionNameRepo;
    private final PortionRepo portionRepo;
    private final CondimentNameRepo condimentNameRepo;
    private final CondimentRepo condimentRepo;
    private final FoodItemRepo foodItemRepo;
    private final RestaurantAddressRepo restaurantAddressRepo;
    private final RestaurantRepo restaurantRepo;
    private final RestaurantOwnerRepo restaurantOwnerRepo;
    private final WorkingHoursRepo workingHoursRepo;
    private final CustomerRepo customerRepo;
    private final OrderRepo orderRepo;
    private final EmployeeFunctionRepo employeeFunctionRepo;
    private final UserRepo userRepo;
    private final ImageRepo imageRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public DatabaseInitializer(CustomerRepo customerRepo,
                               CategoryRepo categoryRepo,
                               OrderRepo orderRepo,
                               PortionNameRepo portionNameRepo,
                               PortionRepo portionRepo,
                               CondimentNameRepo condimentNameRepo,
                               CondimentRepo condimentRepo,
                               FoodItemRepo foodItemRepo,
                               RestaurantAddressRepo restaurantAddressRepo,
                               RestaurantRepo restaurantRepo,
                               RestaurantOwnerRepo restaurantOwnerRepo,
                               WorkingHoursRepo workingHoursRepo,
                               EmployeeFunctionRepo employeeFunctionRepo,
                               UserRepo userRepo, ImageRepo imageRepo) {

        this.categoryRepo = categoryRepo;
        this.portionNameRepo = portionNameRepo;
        this.portionRepo = portionRepo;
        this.condimentNameRepo = condimentNameRepo;
        this.condimentRepo = condimentRepo;
        this.foodItemRepo = foodItemRepo;
        this.restaurantAddressRepo = restaurantAddressRepo;
        this.restaurantRepo = restaurantRepo;
        this.restaurantOwnerRepo = restaurantOwnerRepo;
        this.workingHoursRepo = workingHoursRepo;
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
        this.employeeFunctionRepo = employeeFunctionRepo;
        this.userRepo = userRepo;
        this.imageRepo = imageRepo;
    }

    private final String PONEDJELJAK = "Ponedjeljak";
    private final String UTORAK = "Utorak";
    private final String SRIJEDA = "Srijeda";
    private final String CETVRTAK = "Cetvrtak";
    private final String PETAK = "Petak";
    private final String SUBOTA = "Subota";
    private final String NEDJELJA = "Nedjelja";

    @Transactional
    public void insertFakeData() throws NoSuchAlgorithmException {
        //orderRepo.deleteAll();
        //userRepo.deleteAll();
        if(!customerRepo.findByEmail("josipcutura1997@gmail.com").isPresent()){
            //Role role = roleRepo.findByName("USER");
            Customer customer = new Customer(
                    "Josip",
                    "josipcutura1997@gmail.com",
                    "0923329275",
                    bCryptPasswordEncoder.encode("user")
            );
            customer.setCustomerAddress(List.of(new Address("Address_1", "Imotski", "Croatia")));
            customer.setUserRole(UserRole.CUSTOMER);
            customer.setIsEnabled(true);
            customerRepo.save(customer);

//            customer = new Customer(
//                    "Moroccan_Restaurant",
//                    "mezitestrest@yopmail.com",
//                    "0612345678",
//                    bCryptPasswordEncoder.encode("rest")
//            );
//            customer.setCustomerAddress(List.of(new CustomerAddress("Address_1", "Rabat", "Morocco")));
//            customer.setUserRole(UserRole.RESTAURANT);
//            customer.setEnabled(true);
//            customerRepo.save(customer);
        }


        if(categoryRepo.findAll().isEmpty()){

            //ADD 2 CATEGORIES
            Category category1 = new Category("Category_1", "images/hamburger1.jpg");
            Category category2 = new Category("Category_2", "images/hamburger1.jpg");
            categoryRepo.saveAll(List.of(category1, category2));

            //ADD 3 PORTION NAMES
            PortionName large = new PortionName("Large");
            PortionName medium = new PortionName("Medium");
            PortionName small = new PortionName("Small");
            portionNameRepo.saveAll(List.of(large, medium, small));

            //ADD 3 PORTIONS FOR FOOD_ITEM NUMBER 1
            Portion portion1Food1 = new Portion("1.5", true, small);
            Portion portion2Food1 = new Portion("2", false, medium);
            Portion portion3Food1 = new Portion("3", false, large);
            List<Portion> portionsFood1 = List.of(portion1Food1, portion2Food1, portion3Food1);
            portionRepo.saveAll(portionsFood1);
            //ADD 2 PORTIONS FOR FOOD_ITEM NUMBER 2
            Portion portion1Food2 = new Portion("1", true, small);
            Portion portion2Food2 = new Portion("2.5", false, large);
            List<Portion> portionsFood2 = List.of(portion1Food2, portion2Food2);
            portionRepo.saveAll(portionsFood2);
            //ADD 3 PORTIONS FOR FOOD_ITEM NUMBER 3
            Portion portion1Food3 = new Portion("3", true, small);
            Portion portion2Food3 = new Portion("5.5", false, medium);
            Portion portion3Food3 = new Portion("9", false, large);
            List<Portion> portionsFood3 = List.of(portion1Food3, portion2Food3, portion3Food3);
            portionRepo.saveAll(portionsFood3);

            //ADD 3 CONDIMENT NAMES
            CondimentName ketchup = new CondimentName("Ketchup");
            CondimentName mayonnaise = new CondimentName("Mayonnaise");
            CondimentName salt = new CondimentName("Salt");
            CondimentName sugar = new CondimentName("Sugar");
            condimentNameRepo.saveAll(List.of(ketchup, mayonnaise, salt, sugar));

            //ADD 2 PORTIONS FOR FOOD_ITEM NUMBER 1
            Condiment condiment1Food1 = new Condiment("0", ketchup);
            Condiment condiment2Food1 = new Condiment("0", mayonnaise);
            List<Condiment> condimentsFood1 = List.of(condiment1Food1, condiment2Food1);
            condimentRepo.saveAll(condimentsFood1);
            //ADD 2 PORTIONS FOR FOOD_ITEM NUMBER 2
            Condiment condiment1Food2 = new Condiment("0", sugar);
            Condiment condiment2Food2 = new Condiment("0.5", salt);
            List<Condiment> condimentsFood2 = List.of(condiment1Food2, condiment2Food2);
            condimentRepo.saveAll(condimentsFood2);
            //ADD 3 PORTIONS FOR FOOD_ITEM NUMBER 3
            Condiment condiment1Food3 = new Condiment("0", ketchup);
            Condiment condiment2Food3 = new Condiment("1", mayonnaise);
            Condiment condiment3Food3 = new Condiment("0.5", salt);
            List<Condiment> condimentsFood3 = List.of(condiment1Food3, condiment2Food3, condiment3Food3);
            condimentRepo.saveAll(condimentsFood3);

            //ADD 3 FOOD_ITEMS FOR RESTAURANT NUMBER 1
            FoodItem foodItem1 = new FoodItem(
                    "images/1.jpg",
                    "food_item_1",
                    "Info about food_item_1",
                    category1,
                    portionsFood1,
                    condimentsFood1
            );
            foodItem1.setDefaultPrice();
            FoodItem foodItem2 = new FoodItem(
                    "images/2.jpg",
                    "food_item_2",
                    "Info about food_item_2",
                    category1,
                    portionsFood2,
                    condimentsFood2
            );
            foodItem2.setDefaultPrice();
            FoodItem foodItem3 = new FoodItem(
                    "images/3.jpg",
                    "food_item_3",
                    "Info about food_item_3",
                    category2,
                    portionsFood3,
                    condimentsFood3
            );
            foodItem3.setDefaultPrice();
            List<FoodItem> foodItemsRest1 = List.of(foodItem1, foodItem2, foodItem3);
            foodItemRepo.saveAll(foodItemsRest1);

            //ADD ADDRESS FOR RESTAURANT NUMBER 1
            RestaurantAddress restaurant1Address = new RestaurantAddress("Address_1", "Zagreb", "Croatia");
            restaurantAddressRepo.save(restaurant1Address);

            //ADD RESTAURANT NUMBER 1
            Restaurant restaurant1 = new Restaurant(
                    "0123456789",
                    "images/nuggets1.jpg",
                    "Restaurant_1",
                    "images/nuggets1.jpg",
                    "5",
                    restaurant1Address,
                    foodItemsRest1
            );
            restaurant1.setNotificationReference(ReferenceGenerator.generateReference());
            restaurantRepo.save(restaurant1);

            //ADD 7 WORKING HOURS FOR RESTAURANT NUMBER 1
            WorkingHours monday = new WorkingHours("08:00", "23:00", PONEDJELJAK, restaurant1);
            WorkingHours tuesday = new WorkingHours("08:00", "23:00", UTORAK, restaurant1);
            WorkingHours wednesday = new WorkingHours("09:00", "23:00", SRIJEDA, restaurant1);
            WorkingHours thursday = new WorkingHours("08:00", "23:00", CETVRTAK, restaurant1);
            WorkingHours friday = new WorkingHours("09:00", "23:30", PETAK, restaurant1);
            WorkingHours saturday = new WorkingHours("08:00", "23:30", SUBOTA, restaurant1);
            WorkingHours sunday = new WorkingHours("08:00", "23:00", NEDJELJA, restaurant1);
            List<WorkingHours> workingHoursRest1 = List.of(monday, tuesday, wednesday, thursday, friday, saturday, sunday);
            workingHoursRepo.saveAll(workingHoursRest1);

            //ADD 2 EMPLOYEE FUNCTIONS
            EmployeeFunction waiter = new EmployeeFunction("Waiter");
            employeeFunctionRepo.save(waiter);
            EmployeeFunction manager = new EmployeeFunction("Manager");
            employeeFunctionRepo.save(manager);

            //ADD RESTAURANT OWNER FOR RESTAURANT NUMBER 1
            RestaurantOwner owner1 = new RestaurantOwner(
                    "Moroccan_Owner",
                    "mezitestrest@yopmail.com",
                    "0612345678",
                    bCryptPasswordEncoder.encode("rest")
            );
            owner1.setUserRole(UserRole.SUPER_RESTAURANT);
            owner1.setFunction(manager);
            owner1.setIsEnabled(true);
            owner1.setRestaurant(restaurant1);
            restaurantOwnerRepo.save(owner1);

            System.out.println(restaurantRepo.findByName("Restaurant_1").get().getOwners());

        }
        //INSERT ABOUT US

        imageRepo.save(new Image(1L, "images/o-nama.jpeg"));
        imageRepo.save(new Image(2L, "images/beef.jpg"));
        imageRepo.save(new Image(3L, "images/nuggets1.jpg"));


        userRepo.findAll().stream()
                .forEach(u -> {
                    System.out.println(u.getName() + " / " + u.getUserRole() + " / " + u.getId());
                });

    }

}

