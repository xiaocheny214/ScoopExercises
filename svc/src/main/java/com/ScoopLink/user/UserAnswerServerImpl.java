package com.ScoopLink.user;

import com.ScoopLink.userAnswers.dto.UserAnswer;
import com.ScoopLink.userAnswers.server.UserAnswersServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserAnswerServerImpl implements UserAnswersServer {

    @Resource
    private UserAnswerMapper userAnswerMapper;

    @Override
    public UserAnswer GetUserAnswer(Long id) {
        return userAnswerMapper.selectById(id);
    }

    @Override
    public UserAnswer GetUserAnswerByQuestionId(Integer questionId) {
        return userAnswerMapper.selectByQuestionId(questionId);
    }

    @Override
    public List<UserAnswer> GetUserAnswerList() {
        return userAnswerMapper.selectAll();
    }

    @Override
    @Transactional
    public boolean UpdateUserAnswer(UserAnswer userAnswer) {
        int result = userAnswerMapper.updateUserAnswer(userAnswer);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean DeleteUserAnswer(Long id) {
        int result = userAnswerMapper.deleteById(id);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean DeleteUserAnswerList(List<Long> ids) {
        int result = userAnswerMapper.deleteByIds(ids);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean CreateUserAnswers(UserAnswer userAnswer) {
        int result = userAnswerMapper.insertUserAnswer(userAnswer);
        return result > 0;
    }

    @Override
    public boolean CreateUserAnswers(List<UserAnswer> userAnswerList) {
        if (userAnswerList == null || userAnswerList.isEmpty()) {
            return false;
        }
        int result = userAnswerMapper.insertUserAnswers(userAnswerList);
        return result > 0;
    }
}