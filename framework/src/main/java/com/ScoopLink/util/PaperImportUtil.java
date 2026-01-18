package com.ScoopLink.util;

import com.ScoopLink.manageQuestion.papers.dto.PaperImportTemplate;
import com.ScoopLink.manageQuestion.papers.dto.Paper;
import com.ScoopLink.manageQuestion.multipleChoiceQuestion.dto.MultipleChoiceQuestion;
import com.ScoopLink.manageQuestion.essayQuestions.dto.EssayQuestion;
import com.ScoopLink.manageQuestion.analysisQuestion.dto.AnalysisQuestion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaperImportUtil {
    
    /**
     * 从JSON字符串解析为导入模板
     */
    public static PaperImportTemplate parseFromJson(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, PaperImportTemplate.class);
    }
    
    /**
     * 从Excel文件解析为导入模板
     */
    public static PaperImportTemplate parseFromExcel(String filePath) throws IOException {
        try (InputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            PaperImportTemplate template = new PaperImportTemplate();
            
            // 解析试卷基本信息工作表
            Sheet paperSheet = workbook.getSheet("PaperInfo");
            if (paperSheet != null) {
                Paper paper = parsePaperInfo(paperSheet);
                template.setPaperInfo(paper);
            }
            
            // 解析选择题工作表
            Sheet mcqSheet = workbook.getSheet("MultipleChoiceQuestions");
            if (mcqSheet != null) {
                List<MultipleChoiceQuestion> mcqList = parseMultipleChoiceQuestions(mcqSheet);
                template.setMultipleChoiceQuestions(mcqList);
            }
            
            // 解析解答题工作表
            Sheet eqSheet = workbook.getSheet("EssayQuestions");
            if (eqSheet != null) {
                List<EssayQuestion> eqList = parseEssayQuestions(eqSheet);
                template.setEssayQuestions(eqList);
            }
            
            // 解析分析题工作表
            Sheet aqSheet = workbook.getSheet("AnalysisQuestions");
            if (aqSheet != null) {
                List<AnalysisQuestion> aqList = parseAnalysisQuestions(aqSheet);
                template.setAnalysisQuestions(aqList);
            }
            
            return template;
        }
    }
    
    private static Paper parsePaperInfo(Sheet sheet) {
        Paper paper = new Paper();
        
        // 假设第一行是标题，从第二行开始是数据
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) return paper;
        
        Row dataRow = sheet.getRow(1);
        if (dataRow == null) return paper;
        
        // 根据列名找到对应的列索引
        int idCol = getColumnIndex(headerRow, "id");
        int bankIdCol = getColumnIndex(headerRow, "bankId");
        int titleCol = getColumnIndex(headerRow, "title");
        int descriptionCol = getColumnIndex(headerRow, "description");
        int totalScoreCol = getColumnIndex(headerRow, "totalScore");
        int questionCountCol = getColumnIndex(headerRow, "questionCount");
        int timeLimitCol = getColumnIndex(headerRow, "timeLimit");
        int createTimeCol = getColumnIndex(headerRow, "createTime");
        int updateTimeCol = getColumnIndex(headerRow, "updateTime");
        
        if (idCol != -1) paper.setId(getLongCellValue(dataRow, idCol));
        if (bankIdCol != -1) paper.setBankId(getLongCellValue(dataRow, bankIdCol));
        if (titleCol != -1) paper.setTitle(getStringCellValue(dataRow, titleCol));
        if (descriptionCol != -1) paper.setDescription(getStringCellValue(dataRow, descriptionCol));
        if (totalScoreCol != -1) paper.setTotalScore(getIntCellValue(dataRow, totalScoreCol));
        if (questionCountCol != -1) paper.setQuestionCount(getIntCellValue(dataRow, questionCountCol));
        if (timeLimitCol != -1) paper.setTimeLimit(getIntCellValue(dataRow, timeLimitCol));
        if (createTimeCol != -1) {
            String dateStr = getStringCellValue(dataRow, createTimeCol);
            if (dateStr != null) {
                // 可以根据需要解析日期字符串
                // 这里简单地忽略，因为导入时会自动生成时间
            }
        }
        if (updateTimeCol != -1) {
            String dateStr = getStringCellValue(dataRow, updateTimeCol);
            if (dateStr != null) {
                // 可以根据需要解析日期字符串
                // 这里简单地忽略，因为导入时会自动生成时间
            }
        }
        
        return paper;
    }
    
    private static List<MultipleChoiceQuestion> parseMultipleChoiceQuestions(Sheet sheet) {
        List<MultipleChoiceQuestion> questions = new ArrayList<>();
        
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) return questions;
        
        // 从第二行开始是数据
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            
            MultipleChoiceQuestion question = new MultipleChoiceQuestion();
            
            // 根据列名找到对应的列索引
            int idCol = getColumnIndex(headerRow, "id");
            int paperIdCol = getColumnIndex(headerRow, "paperId");
            int questionTextCol = getColumnIndex(headerRow, "questionText");
            int optionACol = getColumnIndex(headerRow, "optionA");
            int optionBCol = getColumnIndex(headerRow, "optionB");
            int optionCCol = getColumnIndex(headerRow, "optionC");
            int optionDCol = getColumnIndex(headerRow, "optionD");
            int optionECol = getColumnIndex(headerRow, "optionE");
            int optionFCol = getColumnIndex(headerRow, "optionF");
            int correctAnswerCol = getColumnIndex(headerRow, "correctAnswer");
            int explanationCol = getColumnIndex(headerRow, "explanation");
            int scoreCol = getColumnIndex(headerRow, "score");
            int sortOrderCol = getColumnIndex(headerRow, "sortOrder");
            int questionTypeIdCol = getColumnIndex(headerRow, "questionTypeId");
            
            if (idCol != -1) question.setId(getLongCellValue(row, idCol));
            if (paperIdCol != -1) question.setPaperId(getLongCellValue(row, paperIdCol));
            if (questionTextCol != -1) question.setQuestionText(getStringCellValue(row, questionTextCol));
            if (optionACol != -1) question.setOptionA(getStringCellValue(row, optionACol));
            if (optionBCol != -1) question.setOptionB(getStringCellValue(row, optionBCol));
            if (optionCCol != -1) question.setOptionC(getStringCellValue(row, optionCCol));
            if (optionDCol != -1) question.setOptionD(getStringCellValue(row, optionDCol));
            if (optionECol != -1) question.setOptionE(getStringCellValue(row, optionECol));
            if (optionFCol != -1) question.setOptionF(getStringCellValue(row, optionFCol));
            if (correctAnswerCol != -1) question.setCorrectAnswer(getStringCellValue(row, correctAnswerCol));
            if (explanationCol != -1) question.setExplanation(getStringCellValue(row, explanationCol));
            if (scoreCol != -1) question.setScore(getIntCellValue(row, scoreCol));
            if (sortOrderCol != -1) question.setSortOrder(getIntCellValue(row, sortOrderCol));
            if (questionTypeIdCol != -1) question.setQuestionTypeId(getLongCellValue(row, questionTypeIdCol));
            
            questions.add(question);
        }
        
        return questions;
    }
    
    private static List<EssayQuestion> parseEssayQuestions(Sheet sheet) {
        List<EssayQuestion> questions = new ArrayList<>();
        
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) return questions;
        
        // 从第二行开始是数据
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            
            EssayQuestion question = new EssayQuestion();
            
            // 根据列名找到对应的列索引
            int idCol = getColumnIndex(headerRow, "id");
            int paperIdCol = getColumnIndex(headerRow, "paperId");
            int questionTextCol = getColumnIndex(headerRow, "questionText");
            int referenceAnswerCol = getColumnIndex(headerRow, "referenceAnswer");
            int explanationCol = getColumnIndex(headerRow, "explanation");
            int scoreCol = getColumnIndex(headerRow, "score");
            int sortOrderCol = getColumnIndex(headerRow, "sortOrder");
            int questionTypeIdCol = getColumnIndex(headerRow, "questionTypeId");
            
            if (idCol != -1) question.setId(getLongCellValue(row, idCol));
            if (paperIdCol != -1) question.setPaperId(getLongCellValue(row, paperIdCol));
            if (questionTextCol != -1) question.setQuestionText(getStringCellValue(row, questionTextCol));
            if (referenceAnswerCol != -1) question.setReferenceAnswer(getStringCellValue(row, referenceAnswerCol));
            if (explanationCol != -1) question.setExplanation(getStringCellValue(row, explanationCol));
            if (scoreCol != -1) question.setScore(getIntCellValue(row, scoreCol));
            if (sortOrderCol != -1) question.setSortOrder(getIntCellValue(row, sortOrderCol));
            if (questionTypeIdCol != -1) question.setQuestionTypeId(getLongCellValue(row, questionTypeIdCol));
            
            questions.add(question);
        }
        
        return questions;
    }
    
    private static List<AnalysisQuestion> parseAnalysisQuestions(Sheet sheet) {
        List<AnalysisQuestion> questions = new ArrayList<>();
        
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) return questions;
        
        // 从第二行开始是数据
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            
            AnalysisQuestion question = new AnalysisQuestion();
            
            // 根据列名找到对应的列索引
            int idCol = getColumnIndex(headerRow, "id");
            int paperIdCol = getColumnIndex(headerRow, "paperId");
            int questionTextCol = getColumnIndex(headerRow, "questionText");
            int correctAnswerCol = getColumnIndex(headerRow, "correctAnswer");
            int explanationCol = getColumnIndex(headerRow, "explanation");
            int scoreCol = getColumnIndex(headerRow, "score");
            int sortOrderCol = getColumnIndex(headerRow, "sortOrder");
            int questionTypeIdCol = getColumnIndex(headerRow, "questionTypeId");
            
            if (idCol != -1) question.setId(getLongCellValue(row, idCol));
            if (paperIdCol != -1) question.setPaperId(getLongCellValue(row, paperIdCol));
            if (questionTextCol != -1) question.setQuestionText(getStringCellValue(row, questionTextCol));
            if (correctAnswerCol != -1) question.setCorrectAnswer(getStringCellValue(row, correctAnswerCol));
            if (explanationCol != -1) question.setExplanation(getStringCellValue(row, explanationCol));
            if (scoreCol != -1) question.setScore(getIntCellValue(row, scoreCol));
            if (sortOrderCol != -1) question.setSortOrder(getIntCellValue(row, sortOrderCol));
            if (questionTypeIdCol != -1) question.setQuestionTypeId(getLongCellValue(row, questionTypeIdCol));
            
            questions.add(question);
        }
        
        return questions;
    }
    
    private static int getColumnIndex(Row headerRow, String columnName) {
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null && cell.getStringCellValue().equals(columnName)) {
                return i;
            }
        }
        return -1; // 未找到列
    }
    
    private static String getStringCellValue(Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        if (cell == null) return null;
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // 如果是数字，但要作为字符串返回，转换为字符串
                    double numericValue = cell.getNumericCellValue();
                    // 检查是否为整数
                    if (numericValue == Math.floor(numericValue)) {
                        return String.valueOf((long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }
    
    private static Long getLongCellValue(Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        if (cell == null) return null;
        
        switch (cell.getCellType()) {
            case NUMERIC:
                return (long) cell.getNumericCellValue();
            case STRING:
                try {
                    return Long.parseLong(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return null;
                }
            default:
                return null;
        }
    }
    
    private static Integer getIntCellValue(Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        if (cell == null) return null;
        
        switch (cell.getCellType()) {
            case NUMERIC:
                return (int) cell.getNumericCellValue();
            case STRING:
                try {
                    return Integer.parseInt(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return null;
                }
            default:
                return null;
        }
    }
}