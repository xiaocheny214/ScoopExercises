package com.ScoopLink.questionBank.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuestionBank {

    private Long id;


    private String name;

    private String description;

    private Long creatorId;

    private LocalDateTime createTime;
}
