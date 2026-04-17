package com.example.reset;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.reset.model.User;
import com.example.reset.repository.UserRepository;

@SpringBootApplication
public class ResetApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResetApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            User testUser = new User();
            testUser.setEmail("test@example.com");
            testUser.setPassword("oldpassword123");
            repository.save(testUser);
            System.out.println("Test User Created: test@example.com");
        };
    }
}
