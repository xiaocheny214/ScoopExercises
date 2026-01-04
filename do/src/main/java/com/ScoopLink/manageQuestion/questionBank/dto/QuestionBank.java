package com.ScoopLink.manageQuestion.questionBank.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuestionBank {

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
