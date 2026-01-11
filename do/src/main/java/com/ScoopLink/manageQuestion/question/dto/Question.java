package com.ScoopLink.manageQuestion.question.dto;


import com.ScoopLink.manageQuestion.analysisQuestion.dto.AnswerAnalysis;
import com.ScoopLink.manageQuestion.essayQuestions.dto.AnswerEassy;
import com.ScoopLink.manageQuestion.multipleChoiceQuestion.dto.AnswerChoice;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Question implements Serializable {

    private static final long SerialVersionUID =1L;

    /**
     * 问题辨析题
     */
    List<AnswerAnalysis> analysis;

    /**
     * 解答题
     */
    List<AnswerEassy> essay;

    /**
     * 选择题
     */
    List<AnswerChoice> multipleChoice;
}
