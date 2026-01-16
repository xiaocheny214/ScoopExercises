package com.ScoopLink.userAnswers.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAnswer {

    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 试卷ID
     */
    private Long paperId;

    /**
     * 问题类型
     */
    private String questionType;

    /**
     * 问题ID
     */
    private Long questionId;

    /**
     * 用户回答
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
     * 回答时间
     */
    private LocalDateTime answerTime;

    /**
     * 尝试次数
     */
    private Long attemptNum;
}
