package com.ScoopLink.manageQuestion.analysisQuestion.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class AnswerAnalysis implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 问题ID
     */
    private Long questionId;
    /**
     * 问题文本
     */
    private String questionText;

    /**
     * 问题类型
     */
    private String questionType;

    /**
     * 正确答案
     */
    private String correctAnswer;

    /**
     * 用户答案
     */
    private String userAnswer;

    /**
     * 是否正确
     */
    private boolean isCorrect;

    /**
     * 获得分数
     */
    private Integer scoreObtained;

    /**
     * 最大分数
     */
    private Integer maxScore;
}
