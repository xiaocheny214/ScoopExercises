package com.ScoopLink.papers.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class paper {

    private Long id;

    private Long bankId;

    private String title;

    private String description;

    private Integer totalScore;

    private Integer questionCount;

    private LocalDateTime createTime;

}
