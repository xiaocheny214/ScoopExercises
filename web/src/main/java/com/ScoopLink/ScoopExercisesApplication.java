package com.ScoopLink;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ScoopLink") // 明确指定扫描包，避免不必要的组件扫描
@MapperScan(basePackages = {
        "com.ScoopLink.user",
        "com.ScoopLink.multipleChoiceQuestion",
        "com.ScoopLink.essayQuestion",
        "com.ScoopLink.analysisQuestion",
        "com.ScoopLink.paper",
        "com.ScoopLink.questionBankMapper",
        "com.ScoopLink.score",
        "com.ScoopLink.question"}) // 添加question包扫描
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