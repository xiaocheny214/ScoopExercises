package com.ScoopLink.scores.dto;


import lombok.Data;


import java.time.LocalDateTime;

@Data
public class Score {

    private Long id;

    private Long userId;

    private Long paperId;

    private Double totalScore;

    private Integer maxScore;

    private LocalDateTime createTime;
}
