package com.ScoopLink.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.sql.DataSource;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    // 需要检查的关键表
    private static final String[] REQUIRED_TABLES = {
            "users", "question_banks", "papers", "question_types",
            "multiple_choice_questions", "analysis_questions", "essay_questions"
    };

    @Override
    public void run(String... args) throws Exception {
        log.info("开始检查数据库初始化状态...");
        
        if (isDatabaseEmpty()) {
            log.info("检测到空数据库，开始执行初始化脚本...");
            initializeDatabase();
        } else {
            log.info("数据库已初始化，跳过初始化脚本执行");
        }
    }

    /**
     * 检查数据库是否为空
     */
    private boolean isDatabaseEmpty() {
        try {
            Set<String> existingTables = getExistingTables();
            
            // 检查是否所有必需的表都存在
            for (String table : REQUIRED_TABLES) {
                if (!existingTables.contains(table.toUpperCase())) {
                    log.info("检测到缺失表: {}，需要初始化", table);
                    return true;
                }
            }
            
            // 检查是否有基本数据
            Integer userCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM users", Integer.class);
            Integer typeCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM question_types", Integer.class);
                    
            if (userCount == 0 || typeCount == 0) {
                log.info("检测到基础数据缺失，需要初始化");
                return true;
            }
            
            return false;
        } catch (Exception e) {
            log.warn("数据库检查失败，可能需要初始化: {}", e.getMessage());
            return true;
        }
    }

    /**
     * 获取数据库中已存在的表
     */
    private Set<String> getExistingTables() throws Exception {
        Set<String> tables = new HashSet<>();
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME").toUpperCase());
            }
        }
        return tables;
    }

    /**
     * 执行数据库初始化脚本
     */
    private void initializeDatabase() {
        try {
            ClassPathResource resource = new ClassPathResource("scoop_exercises.sql");
            String sqlScript = FileCopyUtils.copyToString(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            
            // 分割SQL语句并执行
            String[] sqlStatements = sqlScript.split(";");
            for (String sql : sqlStatements) {
                String trimmedSql = sql.trim();
                if (!trimmedSql.isEmpty() && !trimmedSql.startsWith("--")) {
                    try {
                        jdbcTemplate.execute(trimmedSql);
                        log.debug("执行SQL: {}", trimmedSql.substring(0, Math.min(50, trimmedSql.length())) + "...");
                    } catch (Exception e) {
                        log.warn("SQL执行失败（可能已存在）: {}", e.getMessage());
                    }
                }
            }
            
            log.info("数据库初始化完成");
        } catch (Exception e) {
            log.error("数据库初始化失败: {}", e.getMessage(), e);
        }
    }
}
