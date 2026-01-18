package com.ScoopLink.manageQuestion.essayQuestions.dto;

import lombok.Data;

@Data
public class EssayQuestion {

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
     * 参考答案
     */
    private String referenceAnswer;

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

     /**
     * 题目类型ID
     */
    private Long questionTypeId;
}
