package com.ScoopLink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScoopExercisesApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ScoopExercisesApplication.class);
        app.setBanner((environment, sourceClass, out) -> {
            out.println("  /\\_/\\  ");
            out.println(" / o o \\ ");
            out.println("|  ^  ^  |");
            out.println(" \\_~_~_/  ");
            out.println("  /   \\  ");
            out.println("────────────────");
            out.println("│ ScoopExercises │");
            out.println("────────────────");
        });
        app.run(args);
    }
}
