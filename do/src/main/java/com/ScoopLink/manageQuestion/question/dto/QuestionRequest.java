package com.ScoopLink.manageQuestion.question.dto;

import lombok.Data;
import java.util.Map;

@Data
public class QuestionRequest {
    /**
     * 题目ID（更新时使用）
     */
    private Long id;

    /**
     * 题目类型ID
     */
    private Long questionTypeId;

    /**
     * 试卷ID
     */
    private Long paperId;

    /**
     * 问题文本
     */
    private String questionText;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 排序顺序
     */
    private Integer sortOrder;

    /**
     * 扩展字段 - 用于存储特定题目类型的参数
     */
    private Map<String, Object> extraFields;

    /**
     * 题目类型代码（用于快速识别题目类型）
     */
    private String questionTypeCode;
}