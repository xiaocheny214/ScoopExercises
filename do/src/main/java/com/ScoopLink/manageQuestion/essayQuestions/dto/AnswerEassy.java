package com.ScoopLink.manageQuestion.essayQuestions.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class AnswerEassy implements Serializable {
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
     * 参考答案
     */
    private String referenceAnswer;

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
