
-- 创建用户表
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     account VARCHAR(50) NOT NULL UNIQUE COMMENT '用户账号',
    nickname VARCHAR(100) NOT NULL COMMENT '昵称',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    os_identifier VARCHAR(100) COMMENT '绑定的操作系统标识',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    login_time TIMESTAMP NULL COMMENT '登录时间'
    );


-- 题库表
CREATE TABLE IF NOT EXISTS question_banks (
                                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                              name VARCHAR(100) NOT NULL,
                                              description TEXT,
                                              creator_id BIGINT NOT NULL,
                                              create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                              FOREIGN KEY (creator_id) REFERENCES users(id)
);

-- 试卷表
CREATE TABLE IF NOT EXISTS papers (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      bank_id BIGINT NOT NULL,
                                      title VARCHAR(200) NOT NULL,
                                      description TEXT,
                                      total_score INT DEFAULT 0,
                                      question_count INT DEFAULT 0,
                                      create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      time_limit INT DEFAULT 0,
                                      update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      FOREIGN KEY (bank_id) REFERENCES question_banks(id)
);

-- 选择题表
CREATE TABLE IF NOT EXISTS multiple_choice_questions (
                                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                         paper_id BIGINT NOT NULL,
                                                         question_text TEXT NOT NULL,
                                                         option_a TEXT NOT NULL,
                                                         option_b TEXT NOT NULL,
                                                         option_c TEXT,
                                                         option_d TEXT,
                                                         option_e TEXT,
                                                         option_f TEXT,
                                                         correct_answer VARCHAR(5) NOT NULL, -- A, B, C, D, E, F 或组合
                                                         explanation TEXT, -- 解析
                                                         score INT DEFAULT 1,
                                                         sort_order INT DEFAULT 0,
                                                         FOREIGN KEY (paper_id) REFERENCES papers(id)
);
alter table multiple_choice_questions add column question_type_id BIGINT DEFAULT 1 COMMENT '题目类型ID，默认1为单选题';

-- 辨析题表
CREATE TABLE IF NOT EXISTS analysis_questions (
                                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                  paper_id BIGINT NOT NULL,
                                                  question_text TEXT NOT NULL,
                                                  correct_answer TEXT,
                                                  explanation TEXT,
                                                  score INT DEFAULT 5,
                                                  sort_order INT DEFAULT 0,
                                                  FOREIGN KEY (paper_id) REFERENCES papers(id)
);
alter table analysis_questions add column question_type_id BIGINT DEFAULT 2 COMMENT '题目类型ID，默认2为辨析题';

-- 解答题表
CREATE TABLE IF NOT EXISTS essay_questions (
                                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                               paper_id BIGINT NOT NULL,
                                               question_text TEXT NOT NULL,
                                               reference_answer TEXT,
                                               explanation TEXT,
                                               score INT DEFAULT 10,
                                               sort_order INT DEFAULT 0,
                                               FOREIGN KEY (paper_id) REFERENCES papers(id)
);
alter table essay_questions add column question_type_id BIGINT DEFAULT 3 COMMENT '题目类型ID，默认3为解答题';

-- 用户答题记录表
CREATE TABLE IF NOT EXISTS user_answers (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            user_id BIGINT NOT NULL,
                                            paper_id BIGINT NOT NULL,
                                            question_type ENUM('MULTIPLE_CHOICE', 'ANALYSIS', 'ESSAY') NOT NULL,
                                            question_id BIGINT NOT NULL,
                                            user_answer TEXT,
                                            is_correct BOOLEAN,
                                            score_obtained DECIMAL(5,2),
                                            answer_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                            FOREIGN KEY (user_id) REFERENCES users(id),
                                            FOREIGN KEY (paper_id) REFERENCES papers(id)
);

drop table if exists scores;
-- 分数统计表
CREATE TABLE IF NOT EXISTS scores (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      user_id BIGINT NOT NULL,
                                      paper_id BIGINT NOT NULL,
                                      total_score_obtained DECIMAL(6,2) DEFAULT 0,
                                      max_score INT DEFAULT 0,
                                      completion_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      status INT DEFAULT 0 COMMENT '状态',
                                      answered_count INT DEFAULT 0 COMMENT '已答题数',
                                      total_count INT DEFAULT 0 COMMENT '总题数',
                                      FOREIGN KEY (user_id) REFERENCES users(id),
                                      FOREIGN KEY (paper_id) REFERENCES papers(id)
);

-- 题目类型表
CREATE TABLE IF NOT EXISTS question_types (
                                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                              type_code VARCHAR(50) NOT NULL UNIQUE COMMENT '题目类型代码，如MULTIPLE_CHOICE, ANALYSIS, ESSAY',
                                              type_name VARCHAR(100) NOT NULL COMMENT '题目类型名称',
                                              description TEXT COMMENT '题目类型描述',
                                              icon VARCHAR(100) COMMENT '类型图标',
                                              sort_order INT DEFAULT 0 COMMENT '排序',
                                              is_active BOOLEAN DEFAULT TRUE COMMENT '是否启用',
                                              create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                              update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 插入默认的题目类型
INSERT INTO question_types (type_code, type_name, description, icon, sort_order, is_active) VALUES
                                                                                                ('MULTIPLE_CHOICE', '选择题', 'Multiple Choice Question', 'mcq-icon', 1, TRUE),
                                                                                                ('ANALYSIS', '分析题', 'Analysis Question', 'analysis-icon', 2, TRUE),
                                                                                                ('ESSAY', '解答题', 'Essay Question', 'essay-icon', 3, TRUE);

-- 插入默认的管理员用户（密码为123456的BCrypt加密值）
INSERT INTO users (account, nickname, password) VALUES
    ('admin@example.com', '系统管理员', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVwE.');