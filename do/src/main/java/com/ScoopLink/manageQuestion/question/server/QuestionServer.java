package com.ScoopLink.manageQuestion.question.server;


import com.ScoopLink.manageQuestion.question.dto.QuestionRequest;
import com.ScoopLink.manageQuestion.question.dto.QuestionResponse;

import java.util.List;

public interface QuestionServer {

    /**
     * 创建题目
     */
    QuestionResponse createQuestion(QuestionRequest request);

    /**
     * 批量创建题目
     */
    List<QuestionResponse> createQuestions(List<QuestionRequest> requests);

    /**
     * 根据ID获取题目
     */
    QuestionResponse getQuestion(Long id, Long questionTypeId);

    /**
     * 根据试卷ID获取题目列表
     */
    List<QuestionResponse> getQuestionsByPaperId(Long paperId);

    /**
     * 根据题目类型获取题目列表
     */
    List<QuestionResponse> getQuestionsByType(Long questionTypeId);

    /**
     * 更新题目
     */
    QuestionResponse updateQuestion(QuestionRequest request);

    /**
     * 删除题目
     */
    QuestionResponse deleteQuestion(Long id, Long questionTypeId);

    /**
     * 批量删除题目
     */
    List<QuestionResponse> deleteQuestions(List<Long> ids, Long questionTypeId);
}
