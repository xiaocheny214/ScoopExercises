package com.ScoopLink.manageQuestion.question.server;

import com.ScoopLink.manageQuestion.question.dto.QuestionType;

import java.util.List;

public interface QuestionTypeServer {
    /**
     * 创建题目类型
     */
    boolean CreateQuestionType(QuestionType questionType);

    /**
     * 根据ID获取题目类型
     */
    QuestionType GetQuestionType(Long id);

    /**
     * 获取所有题目类型
     */
    List<QuestionType> GetAllQuestionTypes();

    /**
     * 更新题目类型
     */
    boolean UpdateQuestionType(QuestionType questionType);

    /**
     * 删除题目类型
     */
    boolean DeleteQuestionType(Long id);

    /**
     * 根据类型代码获取题目类型
     */
    QuestionType GetQuestionTypeByCode(String typeCode);

    /**
     * 获取启用的题目类型
     */
    List<QuestionType> GetActiveQuestionTypes();
}