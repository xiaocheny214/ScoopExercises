# ScoopExercises - 个人题库管理工具

ScoopExercises是一款专注于帮助个人高效管理题库的学习工具，特别适合应对期末考试周等集中复习场景。它解决了个人手中积累的大量题库缺乏有效检验手段的问题，让用户能够随时进行自我测试和学习效果评估。产品注重用户隐私保护，采用本地化数据部署方案，所有题库和个人学习数据均存储在本地设备上，无需担心数据泄露。
## 🚀 主要特性

- **自定义题库管理**：支持创建和管理多个题库，按科目或主题分类
- **多题型支持**：支持选择题、解答题、分析题等多种题型
- **批量导入功能**：支持通过Excel和JSON格式批量导入题目
- **本地数据存储**：使用H2文件数据库，数据完全存储在本地，保障隐私安全
- **跨平台使用**：支持Web端和桌面客户端（Electron打包）
- **智能评分系统**：自动评分和成绩统计功能
- **灵活组卷**：可根据需要自定义试卷内容和分数分配
- ## 🛠️ 技术栈

### 后端技术
- **框架**：Spring Boot 3.2.0
- **语言**：Java 17
- **持久层**：MyBatis
- **数据库**：H2嵌入式数据库（文件模式）
- **连接池**：HikariCP
- **工具**：Lombok, Maven

### 前端技术
- **框架**：Vite构建的前端应用
- **打包**：Electron（桌面应用）
- ## 📁 项目结构

```
ScoopExercises/
├── do/                    # 数据对象层（DTO和实体类）
├── framework/             # 框架工具层
├── repository/            # 数据访问层（MyBatis映射）
├── svc/                   # 业务逻辑层
├── web/                   # Web接口层（REST API）
├── docs/                  # 项目文档
├── data/                  # 本地数据库文件
├── logs/                  # 日志文件
├── fix_db.bat             # 数据库修复脚本
└── pom.xml                # 项目依赖配置
```

## 🔧 快速开始

### 环境要求
- Java 17+
- Maven 3.6+
- ### 构建和运行

1. 克隆项目：
```bash
git clone <repository-url>
cd ScoopExercises
```

2. 构建项目：
```bash
mvn clean install
```

3. 启动应用：
```bash
cd web
mvn spring-boot:run
```

4. 访问应用：
   - Web端：http://localhost:8080
   - 默认端口：8080
### 数据库说明
- 使用H2文件数据库，默认数据存储在 `./data/scoop_exercises_db` 目录
- 数据库文件会持久化保存，重启应用后数据依然存在
- 如需重置数据库，可使用 `fix_db.bat` 脚本（Windows）或手动删除数据文件

## 📱 部署方式
### Web部署
直接运行Spring Boot应用即可，内置Web服务器。

### 桌面应用
使用Electron打包成桌面应用，提供原生应用体验。
## 🔐 隐私与安全

- 所有数据本地存储，不上传至云端
- 用户密码使用BCrypt加密存储
- 支持本地身份验证，无需联网

## 📝 使用指南
# 试卷导入功能说明文档

## API接口
- POST `/api/paper/import/json` - 通过JSON数据导入试卷
- POST `/api/paper/import/excel` - 通过Excel文件导入试卷

 ## 概述
本文档介绍如何创建用于试卷导入的Excel模板，该模板包含4个工作表，分别用于存储试卷基本信息、选择题、解答题和分析题。

## Excel文件结构
Excel文件应包含以下4个工作表，每个工作表的列结构如下：

---

### 1. PaperInfo 工作表（试卷基本信息）

| 列标题 | 说明 | 类型 | 是否必需 | 备注 |
|--------|------|------|----------|------|
| id | 试卷ID | 数字 | 否 | 导入时留空，系统自动生成 |
| bankId | 题库ID | 数字 | 是 | 关联的题库ID |
| title | 试卷标题 | 文本 | 是 | 试卷的标题 |
| description | 试卷描述 | 文本 | 否 | 试卷的描述信息 |
| totalScore | 总分 | 数字 | 否 | 导入时会自动计算 |
| questionCount | 题目数量 | 数字 | 否 | 导入时会自动计算 |
| timeLimit | 时间限制（分钟） | 数字 | 否 | 考试时间限制 |
| createTime | 创建时间 | 日期/文本 | 否 | 导入时会自动生成 |
| updateTime | 更新时间 | 日期/文本 | 否 | 导入时会自动生成 |

**示例数据：**

| id | bankId | title | description | totalScore | questionCount | timeLimit | createTime | updateTime |
|----|--------|-------|-------------|------------|---------------|-----------|------------|------------|
| | 1 | 示例试卷 | 这是一个示例试卷 | 0 | 0 | 120 | | |

---

### 2. MultipleChoiceQuestions 工作表（多选题）

| 列标题 | 说明 | 类型 | 是否必需 | 备注 |
|--------|------|------|----------|------|
| id | 题目ID | 数字 | 否 | 导入时留空，系统自动生成 |
| paperId | 试卷ID | 数字 | 否 | 导入时自动填充 |
| questionText | 问题文本 | 文本 | 是 | 题目的问题内容 |
| optionA | 选项A | 文本 | 是 | 选择题的选项A |
| optionB | 选项B | 文本 | 是 | 选择题的选项B |
| optionC | 选项C | 文本 | 否 | 选择题的选项C（可选） |
| optionD | 选项D | 文本 | 否 | 选择题的选项D（可选） |
| optionE | 选项E | 文本 | 否 | 选择题的选项E（可选） |
| optionF | 选项F | 文本 | 否 | 选择题的选项F（可选） |
| correctAnswer | 正确答案 | 文本 | 是 | 如"A", "B", "ABCD"等 |
| explanation | 解释说明 | 文本 | 否 | 题目的解释说明 |
| score | 分数 | 数字 | 是 | 该题的分数 |
| sortOrder | 排序顺序 | 数字 | 否 | 题目在试卷中的排序 |
| questionTypeId | 题目类型ID | 数字 | 否 | 导入时自动填充 |

**示例数据：**

| id | paperId | questionText | optionA | optionB | optionC | optionD | optionE | optionF | correctAnswer | explanation | score | sortOrder | questionTypeId |
|----|---------|--------------|---------|---------|---------|---------|---------|---------|---------------|-------------|-------|-----------|----------------|
| | | 以下哪个是Java的关键字？ | static | camelCase | variable | method | | | A | static是Java语言的关键字之一 | 5 | 1 | |

---

### 3. EssayQuestions 工作表（解答题）

| 列标题 | 说明 | 类型 | 是否必需 | 备注 |
|--------|------|------|----------|------|
| id | 题目ID | 数字 | 否 | 导入时留空，系统自动生成 |
| paperId | 试卷ID | 数字 | 否 | 导入时自动填充 |
| questionText | 问题文本 | 文本 | 是 | 题目的问题内容 |
| referenceAnswer | 参考答案 | 文本 | 是 | 解答题的参考答案 |
| explanation | 解释说明 | 文本 | 否 | 题目的解释说明 |
| score | 分数 | 数字 | 是 | 该题的分数 |
| sortOrder | 排序顺序 | 数字 | 否 | 题目在试卷中的排序 |
| questionTypeId | 题目类型ID | 数字 | 否 | 导入时自动填充 |

**示例数据：**

| id | paperId | questionText | referenceAnswer | explanation | score | sortOrder | questionTypeId |
|----|---------|--------------|------------------|-------------|-------|-----------|----------------|
| | | 请简述面向对象编程的四大基本特征。 | 面向对象编程的四大基本特征是封装、继承、多态和抽象。 | 这道题考察面向对象编程的基本概念 | 15 | 2 | |

---

### 4. AnalysisQuestions 工作表（分析题）

| 列标题 | 说明 | 类型 | 是否必需 | 备注 |
|--------|------|------|----------|------|
| id | 题目ID | 数字 | 否 | 导入时留空，系统自动生成 |
| paperId | 试卷ID | 数字 | 否 | 导入时自动填充 |
| questionText | 问题文本 | 文本 | 是 | 题目的问题内容 |
| correctAnswer | 正确答案 | 文本 | 是 | 分析题的正确答案 |
| explanation | 解释说明 | 文本 | 否 | 题目的解释说明 |
| score | 分数 | 数字 | 是 | 该题的分数 |
| sortOrder | 排序顺序 | 数字 | 否 | 题目在试卷中的排序 |
| questionTypeId | 题目类型ID | 数字 | 否 | 导入时自动填充 |

**示例数据：**

| id | paperId | questionText | correctAnswer | explanation | score | sortOrder | questionTypeId |
|----|---------|--------------|---------------|-------------|-------|-----------|----------------|
| | | 分析下面代码段的时间复杂度，并提出优化方案。 | 该代码段的时间复杂度为O(n^2)，可以通过使用哈希表将时间复杂度优化到O(n)。 | 本题考察算法时间和空间复杂度分析能力 | 20 | 3 | |

---

## 创建步骤

1. 打开Microsoft Excel或其他兼容的电子表格软件
2. 创建一个新的工作簿
3. 重命名第一个工作表为"PaperInfo"
4. 按照上面的格式创建表头
5. 重复步骤3-4，创建"MultipleChoiceQuestions"、"EssayQuestions"和"AnalysisQuestions"工作表
6. 按照相应格式添加表头
7. 在每个工作表的第2行开始输入你的数据
8. 保存文件为.xlsx格式

## 重要提示

- 工作表名称必须完全匹配：PaperInfo、MultipleChoiceQuestions、EssayQuestions、AnalysisQuestions
- 列标题必须完全匹配，区分大小写
- 所有ID字段（id、paperId、questionTypeId）在导入时通常留空，系统会自动生成或自动填充
- 分数字段必须为数字类型
- 文件必须保存为.xlsx格式
- 不要删除或重命名列标题
- 可以在同一个工作表中添加多行数据（多道题）

## 常见问题

1. **为什么某些字段在导入时要留空？**
   - id字段：系统自动生成唯一标识
   - paperId字段：导入时系统会自动关联到新创建的试卷
   - questionTypeId字段：系统根据题目类型自动分配

2. **如何验证模板格式是否正确？**
   - 确认工作表名称拼写正确
   - 确认列标题拼写正确
   - 确认数据类型正确（数字字段不能包含文本等）
   - 可以先导入少量数据进行测试
## 功能概述
试卷导入功能允许用户通过JSON数据批量创建试卷及其相关题目，包括选择题、解答题和分析题三种类型。

## 数据结构说明

### 主体结构
```json
{
  "paperInfo": {...},           // 试卷基本信息
  "multipleChoiceQuestions": [], // 多选题列表
  "essayQuestions": [],         // 解答题列表
  "analysisQuestions": []       // 分析题列表
}
```

### 试卷信息 (paperInfo)
| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | Long | 否 | 试卷ID，导入时设为null，系统自动生成 |
| bankId | Long | 是 | 题库ID |
| title | String | 是 | 试卷标题 |
| description | String | 否 | 试卷描述 |
| totalScore | Integer | 否 | 总分，导入时设为0，系统自动计算 |
| questionCount | Integer | 否 | 题目数量，导入时设为0，系统自动计算 |
| timeLimit | Integer | 否 | 时间限制（分钟） |
| createTime | LocalDateTime | 否 | 创建时间，导入时设为null，系统自动生成 |
| updateTime | LocalDateTime | 否 | 更新时间，导入时设为null，系统自动生成 |

### 多选题 (multipleChoiceQuestions)
| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | Long | 否 | 题目ID，导入时设为null，系统自动生成 |
| paperId | Long | 否 | 试卷ID，导入时设为null，系统自动填充 |
| questionText | String | 是 | 问题文本 |
| optionA | String | 是 | 选项A |
| optionB | String | 是 | 选项B |
| optionC | String | 否 | 选项C（可选） |
| optionD | String | 否 | 选项D（可选） |
| optionE | String | 否 | 选项E（可选） |
| optionF | String | 否 | 选项F（可选） |
| correctAnswer | String | 是 | 正确答案（如"A", "B", "ABCD"等） |
| explanation | String | 否 | 解释说明 |
| score | Integer | 是 | 分数 |
| sortOrder | Integer | 否 | 排序顺序 |
| questionTypeId | Integer | 否 | 题目类型ID，导入时设为null，系统自动填充 |

### 解答题 (essayQuestions)
| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | Long | 否 | 题目ID，导入时设为null，系统自动生成 |
| paperId | Long | 否 | 试卷ID，导入时设为null，系统自动填充 |
| questionText | String | 是 | 问题文本 |
| referenceAnswer | String | 是 | 参考答案 |
| explanation | String | 否 | 解释说明 |
| score | Integer | 是 | 分数 |
| sortOrder | Integer | 否 | 排序顺序 |
| questionTypeId | Integer | 否 | 题目类型ID，导入时设为null，系统自动填充 |

### 分析题 (analysisQuestions)
| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | Long | 否 | 题目ID，导入时设为null，系统自动生成 |
| paperId | Long | 否 | 试卷ID，导入时设为null，系统自动填充 |
| questionText | String | 是 | 问题文本 |
| correctAnswer | String | 是 | 正确答案 |
| explanation | String | 否 | 解释说明 |
| score | Integer | 是 | 分数 |
| sortOrder | Integer | 否 | 排序顺序 |
| questionTypeId | Integer | 否 | 题目类型ID，导入时设为null，系统自动填充 |

## 导入流程
1. 系统首先计算所有题目的总分和数量
2. 创建试卷并获取试卷ID
3. 将试卷ID分配给所有题目
4. 批量创建各类题目
5. 更新试卷的总分和题目数量

## 注意事项
- 所有ID字段在导入时应设置为null，系统会自动生成
- 试卷的总分和题目数量会在导入完成后自动计算并更新
- 题目类型ID会在导入过程中由系统自动填充
- 试卷创建时间会在导入时自动生成

## 示范
假设有一个JSON数据如下：
```json
{
  "paperInfo": {
    "id": null,
    "bankId": 1,
    "title": "示例试卷",
    "description": "这是一份示例试卷，用于测试导入功能",
    "totalScore": 0,
    "questionCount": 0,
    "timeLimit": 120,
    "createTime": null,
    "updateTime": null
  },
  "multipleChoiceQuestions": [
    {
      "id": null,
      "paperId": null,
      "questionText": "以下哪个是Java的关键字？",
      "optionA": "static",
      "optionB": "camelCase",
      "optionC": "variable",
      "optionD": "method",
      "correctAnswer": "A",
      "explanation": "static是Java语言的关键字之一",
      "score": 5,
      "sortOrder": 1,
      "questionTypeId": null
    },
    {
      "id": null,
      "paperId": null,
      "questionText": "Spring框架的核心特性是什么？",
      "optionA": "面向对象编程",
      "optionB": "依赖注入和控制反转",
      "optionC": "多线程处理",
      "optionD": "异常处理机制",
      "correctAnswer": "B",
      "explanation": "Spring框架的核心特性是依赖注入(DI)和控制反转(IoC)",
      "score": 5,
      "sortOrder": 2,
      "questionTypeId": null
    }
  ],
  "essayQuestions": [
    {
      "id": null,
      "paperId": null,
      "questionText": "请简述面向对象编程的四大基本特征。",
      "referenceAnswer": "面向对象编程的四大基本特征是封装、继承、多态和抽象。",
      "explanation": "这道题考察面向对象编程的基本概念",
      "score": 15,
      "sortOrder": 3,
      "questionTypeId": null
    }
  ],
  "analysisQuestions": [
    {
      "id": null,
      "paperId": null,
      "questionText": "分析下面代码段的时间复杂度，并提出优化方案。",
      "correctAnswer": "该代码段的时间复杂度为O(n^2)，可以通过使用哈希表将时间复杂度优化到O(n)。",
      "explanation": "本题考察算法时间和空间复杂度分析能力",
      "score": 20,
      "sortOrder": 4,
      "questionTypeId": null
    }
  ]
}
