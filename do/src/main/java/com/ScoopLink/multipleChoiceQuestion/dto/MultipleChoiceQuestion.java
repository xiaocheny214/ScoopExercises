package com.ScoopLink.multipleChoiceQuestion.dto;

import lombok.Data;

@Data
public class MultipleChoiceQuestion {

    private Long id;

    private Long paperId;

    private String questionText;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String optionE;

    private String optionF;

    private String correctAnswer;

    private String explanation;

    private Integer score;

    private Integer sortOrder;
}
