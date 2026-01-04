package com.ScoopLink;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ScoopLink.user") // 扫描Mapper接口所在的包
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