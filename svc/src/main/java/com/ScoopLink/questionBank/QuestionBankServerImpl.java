package com.ScoopLink.questionBank;

import com.ScoopLink.manageQuestion.questionBank.dto.QuestionBank;
import com.ScoopLink.manageQuestion.questionBank.server.QuestionBankServer;
import com.ScoopLink.questionBankMapper.QuestionBankMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionBankServerImpl implements QuestionBankServer {

    @Resource
    private QuestionBankMapper questionBankMapper;
    
    @Override
    public QuestionBank GetQuestionBank(Long id) {
        return questionBankMapper.selectById(id);
    }

    @Override
    @Transactional
    public boolean CreateQuestionBank(QuestionBank questionBank) {
        int result = questionBankMapper.insertQuestionBank(questionBank);
        return result > 0;
    }

    @Override
    public List<QuestionBank> GetQuestionBankList() {
        return questionBankMapper.selectAll();
    }

    @Override
    @Transactional
    public boolean UpdateQuestionBank(QuestionBank questionBankServer) {
        int result = questionBankMapper.updateQuestionBank(questionBankServer);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean DeleteQuestionBank(Long id) {
        int result = questionBankMapper.deleteById(id);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean DeleteQuestionBankList(List<Long> ids) {
        int result = questionBankMapper.deleteByIds(ids);
        return result > 0;
    }
}