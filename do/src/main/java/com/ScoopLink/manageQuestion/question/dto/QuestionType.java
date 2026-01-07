package com.ScoopLink.manageQuestion.question.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionType implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 题目类型代码
     */
    private String typeCode;

    /**
     * 题目类型名称
     */
    private String typeName;

    /**
     * 题目类型描述
     */
    private String description;

    /**
     * 类型图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 是否启用
     */
    private Boolean isActive;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
