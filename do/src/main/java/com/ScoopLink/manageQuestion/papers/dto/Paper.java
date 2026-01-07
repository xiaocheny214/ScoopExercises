package com.ScoopLink.manageQuestion.papers.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paper implements Serializable {

    private static final long serialVersionUID = 1L;

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
