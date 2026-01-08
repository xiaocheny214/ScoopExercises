package com.ScoopLink.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.File;

@Component
public class SimpleDatabaseCheck implements CommandLineRunner {
    
    @Override
    public void run(String... args) throws Exception {
        File dbFile = new File("./data/scoop_exercises_db.mv.db");
        if (dbFile.exists()) {
            System.out.println("数据库文件大小: " + dbFile.length() + " 字节");
        } else {
            System.out.println("将创建新的数据库文件");
        }
    }
}