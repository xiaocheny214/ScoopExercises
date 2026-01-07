package com.ScoopLink.manageQuestion.analysisQuestion.dto;

import lombok.Data;

@Data
public class AnalysisQuestion {

    private Long id;

    /**
     * 试卷ID
     */
    private Long paperId;

    /**
     * 问题文本
     */
    private String questionText;

    /**
     * 正确答案
     */
    private String correctAnswer;

    /**
     * 解释说明
     */
    private String explanation;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 排序顺序
     */
    private Integer sortOrder;


    private Integer questionTypeId;
}
