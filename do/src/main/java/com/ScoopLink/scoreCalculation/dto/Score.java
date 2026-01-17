package com.ScoopLink.scoreCalculation.dto;


import lombok.Data;


import java.time.LocalDateTime;

@Data
public class Score {

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
     * 总分数
     */
    private Integer totalScore;

    /**
     * 最大分数
     */
    private Integer maxScore;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

     /**
     * 状态
     */
    private Integer status;

     /**
     * 已答题数
     */
    private Integer answeredCount;

     /**
     * 总题数
     */
    private Integer totalCount;

    /**
     * 作答标识
     */
    private Long attemptNum;
}
