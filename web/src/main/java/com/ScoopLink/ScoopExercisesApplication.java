package com.ScoopLink;


import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScoopExercisesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScoopExercisesApplication.class, args);
    }

    @PostConstruct
    private static final void printWelcomeMessage() {
        System.out.println("Welcome to ScoopLink!");
    }
}
