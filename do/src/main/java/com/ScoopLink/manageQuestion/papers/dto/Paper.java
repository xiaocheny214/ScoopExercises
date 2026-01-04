package com.ScoopLink.manageQuestion.papers.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Paper {

    private Long id;

    /**
     * 试卷库ID
     */
    private Long bankId;

    /**
     * 试卷标题
     */
    private String title;

    /**
     * 试卷描述
     */
    private String description;

    /**
     * 试卷总分数
     */
    private Integer totalScore;

    /**
     * 试卷问题数量
     */
    private Integer questionCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
