package com.ScoopLink.userAnswers.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAnswer {

    private Long id;

    private Long userId;

    private Long paperId;

    private String questionType;

    private Long questionId;

    private String userAnswer;

    private boolean isCorrect;

    private Double scoreObtained;

    private LocalDateTime answerTime;
}
