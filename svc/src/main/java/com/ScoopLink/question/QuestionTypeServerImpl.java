package com.ScoopLink.question;

import com.ScoopLink.manageQuestion.question.dto.QuestionType;
import com.ScoopLink.manageQuestion.question.server.QuestionTypeServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionTypeServerImpl implements QuestionTypeServer {

    @Autowired
    private QuestionTypeMapper questionTypeMapper;

    @Override
    public boolean CreateQuestionType(QuestionType questionType) {
        int result = questionTypeMapper.insertQuestionType(questionType);
        return result > 0;
    }

    @Override
    public QuestionType GetQuestionType(Long id) {
        return questionTypeMapper.selectById(id);
    }

    @Override
    public List<QuestionType> GetAllQuestionTypes() {
        return questionTypeMapper.selectAll();
    }

    @Override
    public boolean UpdateQuestionType(QuestionType questionType) {
        int result = questionTypeMapper.updateQuestionType(questionType);
        return result > 0;
    }

    @Override
    public boolean DeleteQuestionType(Long id) {
        int result = questionTypeMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public QuestionType GetQuestionTypeByCode(String typeCode) {
        return questionTypeMapper.selectByTypeCode(typeCode);
    }

    @Override
    public List<QuestionType> GetActiveQuestionTypes() {
        return questionTypeMapper.selectActiveTypes();
    }
}