package com.ScoopLink.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
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
            "multiple_choice_questions", "analysis_questions", "essay_questions",
            "user_answers","scores"
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
        // 定义要执行的SQL语句数组
        String[] sqlStatements = {
            // 创建题目类型表
            "CREATE TABLE IF NOT EXISTS question_types (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "type_code VARCHAR(50) NOT NULL UNIQUE," +
                "type_name VARCHAR(100) NOT NULL," +
                "description TEXT," +
                "icon VARCHAR(100)," +
                "sort_order INT DEFAULT 0," +
                "is_active BOOLEAN DEFAULT TRUE," +
                "create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
            ")",

            // 创建用户表
            "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "account VARCHAR(50) NOT NULL UNIQUE," +
                "nickname VARCHAR(100) NOT NULL," +
                "password VARCHAR(255) NOT NULL," +
                "os_identifier VARCHAR(100)," +
                "create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "login_time TIMESTAMP NULL" +
            ")",

            // 题库表
            "CREATE TABLE IF NOT EXISTS question_banks (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "description TEXT," +
                "creator_id BIGINT NOT NULL," +
                "create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (creator_id) REFERENCES users(id)" +
            ")",

            // 试卷表
            "CREATE TABLE IF NOT EXISTS papers (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "bank_id BIGINT NOT NULL," +
                "title VARCHAR(200) NOT NULL," +
                "description TEXT," +
                "total_score INT DEFAULT 0," +
                "question_count INT DEFAULT 0," +
                "create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "time_limit INT DEFAULT 0," +
                "update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                "FOREIGN KEY (bank_id) REFERENCES question_banks(id)" +
            ")",

            // 选择题表
            "CREATE TABLE IF NOT EXISTS multiple_choice_questions (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "paper_id BIGINT NOT NULL," +
                "question_text TEXT NOT NULL," +
                "option_a TEXT NOT NULL," +
                "option_b TEXT NOT NULL," +
                "option_c TEXT," +
                "option_d TEXT," +
                "option_e TEXT," +
                "option_f TEXT," +
                "correct_answer VARCHAR(5) NOT NULL," +
                "explanation TEXT," +
                "score INT DEFAULT 1," +
                "sort_order INT DEFAULT 0," +
                "question_type_id BIGINT DEFAULT 1," +
                "FOREIGN KEY (paper_id) REFERENCES papers(id)" +
            ")",

            // 辨析题表
            "CREATE TABLE IF NOT EXISTS analysis_questions (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "paper_id BIGINT NOT NULL," +
                "question_text TEXT NOT NULL," +
                "correct_answer TEXT," +
                "explanation TEXT," +
                "score INT DEFAULT 5," +
                "sort_order INT DEFAULT 0," +
                "question_type_id BIGINT DEFAULT 2," +
                "FOREIGN KEY (paper_id) REFERENCES papers(id)" +
            ")",

            // 解答题表
            "CREATE TABLE IF NOT EXISTS essay_questions (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "paper_id BIGINT NOT NULL," +
                "question_text TEXT NOT NULL," +
                "reference_answer TEXT," +
                "explanation TEXT," +
                "score INT DEFAULT 10," +
                "sort_order INT DEFAULT 0," +
                "question_type_id BIGINT DEFAULT 3," +
                "FOREIGN KEY (paper_id) REFERENCES papers(id)" +
            ")",

            // 用户答题记录表
            "CREATE TABLE IF NOT EXISTS user_answers (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "user_id BIGINT NOT NULL," +
                "paper_id BIGINT NOT NULL," +
                "question_type ENUM('MULTIPLE_CHOICE', 'ANALYSIS', 'ESSAY') NOT NULL," +
                "question_id BIGINT NOT NULL," +
                "user_answer TEXT," +
                "is_correct BOOLEAN," +
                "score_obtained DECIMAL(5,2)," +
                "attempt_num BIGINT NOT NULL DEFAULT 1," +
                "answer_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (user_id) REFERENCES users(id)," +
                "FOREIGN KEY (paper_id) REFERENCES papers(id)" +
            ")",

            // 分数统计表
            "CREATE TABLE IF NOT EXISTS scores (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "user_id BIGINT NOT NULL," +
                "paper_id BIGINT NOT NULL," +
                "total_score_obtained DECIMAL(6,2) DEFAULT 0," +
                "max_score INT DEFAULT 0," +
                "completion_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "status INT DEFAULT 0," +
                "answered_count INT DEFAULT 0," +
                "total_count INT DEFAULT 0," +
                "attempt_num BIGINT NOT NULL DEFAULT 1," +
                "FOREIGN KEY (user_id) REFERENCES users(id)," +
                "FOREIGN KEY (paper_id) REFERENCES papers(id)" +
            ")",

            // 插入默认的题目类型
            "INSERT INTO question_types (type_code, type_name, description, icon, sort_order, is_active) VALUES " +
            "('MULTIPLE_CHOICE', 'Multiple Choice', 'Multiple Choice Question', 'mcq-icon', 1, TRUE), " +
            "('ANALYSIS', 'Analysis', 'Analysis Question', 'analysis-icon', 2, TRUE), " +
            "('ESSAY', 'Essay', 'Essay Question', 'essay-icon', 3, TRUE), " +
            "('SINGLE_CHOICE', 'Single Choice', 'Single Choice Question', 'scq-icon', 4, TRUE)",

            // 插入默认的管理员用户（密码为123456的BCrypt加密值）
            "INSERT INTO users (account, nickname, password) VALUES " +
            "('admin@example.com', 'Admin User', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVwE.')"
        };

        int successCount = 0;
        int totalCount = sqlStatements.length;

        for (String sql : sqlStatements) {
            try {
                jdbcTemplate.execute(sql);
                log.debug("执行SQL成功: {}", sql.substring(0, Math.min(50, sql.length())) + "...");
                successCount++;
            } catch (Exception e) {
                log.warn("SQL执行失败: {}, 语句: {}", e.getMessage(), sql);
            }
        }

        log.info("数据库初始化完成，成功执行 {} 条语句，总共 {} 条", successCount, totalCount);
    }
}