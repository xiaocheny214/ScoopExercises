package com.ScoopLink.manageQuestion.questionBank.dto;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class QuestionBank implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 题库名称
     */
    private String name;
    /**
     * 题库描述
     */
    private String description;

     /**
      * 创建者ID
      */
    private Long creatorId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
