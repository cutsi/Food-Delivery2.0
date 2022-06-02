package com.example.fooddelivery2_0;

import com.example.fooddelivery2_0.Utils.DatabaseInitializer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
@EntityScan
@RequiredArgsConstructor
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    private final DatabaseInitializer databaseInitializer;

    @Bean
    public CommandLineRunner init(){

        return args -> {

            databaseInitializer.insertFakeData();

        };

    }
}
