package com.ScoopLink.userAnswers.server;

import com.ScoopLink.scoreCalculation.dto.Score;
import com.ScoopLink.userAnswers.dto.UserAnswer;
import com.ScoopLink.userAnswers.dto.submitPaper;

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
    /**
     * 提交试卷
     * @param submitPaper 提交的试卷
     * @return 试卷得分
     */
    Score SubmitPaper(submitPaper submitPaper);
}
