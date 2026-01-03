package com.ScoopLink.analysisQuestion.dto;

import lombok.Data;

@Data
public class AnalysisQuestion {

    private Long id;

    private Long paperId;

    private String questionText;

    private String correctAnswer;

    private String explanation;

    private Integer score;

    private Integer sortOrder;
}
