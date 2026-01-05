package com.ScoopLink.userAnswers.server;

import com.ScoopLink.userAnswers.dto.UserAnswer;

import java.util.List;

public interface SubmitAnswer {

    /**
     * 提交用户答案
     * @param userAnswer 用户答案
     * @return 提交结果
     */
    public UserAnswer SubmitAnswer(UserAnswer userAnswer);

    /**
     * 提交用户答案列表
     * @param userAnswerList 用户答案列表
     * @return 提交结果
     */
    public UserAnswer SubmitAnswers(List<UserAnswer> userAnswerList);
}
