package com.ScoopLink.userAnswers.server;

import com.ScoopLink.userAnswers.dto.UserAnswer;

import java.util.List;

public interface SubmitAnswer {


    public UserAnswer SubmitAnswer(UserAnswer userAnswer);


    public UserAnswer SubmitAnswers(List<UserAnswer> userAnswerList);
}
