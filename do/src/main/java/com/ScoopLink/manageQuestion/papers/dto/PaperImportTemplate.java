package com.ScoopLink.manageQuestion.papers.dto;

import com.ScoopLink.manageQuestion.analysisQuestion.dto.AnalysisQuestion;
import com.ScoopLink.manageQuestion.essayQuestions.dto.EssayQuestion;
import com.ScoopLink.manageQuestion.multipleChoiceQuestion.dto.MultipleChoiceQuestion;
import lombok.Data;

import java.util.List;


@Data
public class PaperImportTemplate {

    /**
     * 试卷信息
     */
    private Paper paperInfo;

    /**
     * 多选题列表
     */
    private List<MultipleChoiceQuestion> multipleChoiceQuestions;
    /**
     * 简答题列表
     */
    private List<EssayQuestion> essayQuestions;

    /**
     * 分析题列表
     */
    private List<AnalysisQuestion> analysisQuestions;

    // private List<OtherQuestionType> otherQuestions;
}
