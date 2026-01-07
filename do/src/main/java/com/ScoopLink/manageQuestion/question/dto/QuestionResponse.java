package com.ScoopLink.manageQuestion.question.dto;

import lombok.Data;
import java.util.Map;

@Data
public class QuestionResponse {
    /**
     * 题目ID
     */
    private Long id;

    /**
     * 题目类型ID
     */
    private Long questionTypeId;

    /**
     * 题目类型代码
     */
    private String questionTypeCode;

    /**
     * 题目类型名称
     */
    private String questionTypeName;

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
     * 扩展字段 - 包含特定题目类型的详细信息
     */
    private Map<String, Object> details;

    /**
     * 操作结果
     */
    private Boolean success;

    /**
     * 错误信息
     */
    private String errorMessage;
}