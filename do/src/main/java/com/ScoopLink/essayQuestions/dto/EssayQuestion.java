package com.ScoopLink.essayQuestions.dto;

import lombok.Data;

@Data
public class EssayQuestion {

    private Long id;

    private Long paperId;

    private String questionText;

    private String referenceAnswer;

    private String explanation;

    private Integer score;

    private Integer sortOrder;
}
