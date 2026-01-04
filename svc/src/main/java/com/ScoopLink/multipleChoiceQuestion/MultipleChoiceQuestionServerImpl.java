package com.ScoopLink.multipleChoiceQuestion;

import com.ScoopLink.manageQuestion.multipleChoiceQuestion.dto.MultipleChoiceQuestion;
import com.ScoopLink.manageQuestion.multipleChoiceQuestion.server.MultipleChoiceQuestionServer;
import com.ScoopLink.multipleChoiceQuestion.MultipleChoiceQuestionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MultipleChoiceQuestionServerImpl implements MultipleChoiceQuestionServer {

    @Resource
    private MultipleChoiceQuestionMapper multipleChoiceQuestionMapper;

    @Override
    @Transactional
    public boolean CreateMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
        int result = multipleChoiceQuestionMapper.insertMultipleChoiceQuestion(multipleChoiceQuestion);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean CreateMultipleChoiceQuestions(List<MultipleChoiceQuestion> multipleChoiceQuestions) {
        int result = multipleChoiceQuestionMapper.insertMultipleChoiceQuestions(multipleChoiceQuestions);
        return result > 0;
    }

    @Override
    public MultipleChoiceQuestion GetMultipleChoiceQuestion(Long id) {
        return multipleChoiceQuestionMapper.selectById(id);
    }

    @Override
    @Transactional
    public boolean UpdateMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
        int result = multipleChoiceQuestionMapper.updateMultipleChoiceQuestion(multipleChoiceQuestion);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean DeleteMultipleChoiceQuestion(Long id) {
        int result = multipleChoiceQuestionMapper.deleteById(id);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean DeleteMultipleChoiceQuestionList(List<Long> ids) {
        int result = multipleChoiceQuestionMapper.deleteByIds(ids);
        return result > 0;
    }
}