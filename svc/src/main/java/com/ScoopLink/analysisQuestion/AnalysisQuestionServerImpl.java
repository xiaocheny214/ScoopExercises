package com.ScoopLink.analysisQuestion;

import com.ScoopLink.manageQuestion.analysisQuestion.dto.AnalysisQuestion;
import com.ScoopLink.manageQuestion.analysisQuestion.server.AnalysisQuestionServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnalysisQuestionServerImpl implements AnalysisQuestionServer {

    @Resource
    private AnalysisQuestionMapper analysisQuestionMapper;
    
    @Override
    @Transactional
    public boolean CreateAnalysisQuestion(AnalysisQuestion analysisQuestion) {
        int result = analysisQuestionMapper.insertAnalysisQuestion(analysisQuestion);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean CreateAnalysisQuestions(List<AnalysisQuestion> analysisQuestions) {
        int result = analysisQuestionMapper.insertAnalysisQuestions(analysisQuestions);
        return result > 0;
    }

    @Override
    public AnalysisQuestion GetAnalysisQuestion(Long id) {
        return analysisQuestionMapper.selectById(id);
    }

    @Override
    @Transactional
    public boolean UpdateAnalysisQuestion(AnalysisQuestion analysisQuestion) {
        int result = analysisQuestionMapper.updateAnalysisQuestion(analysisQuestion);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean DeleteAnalysisQuestion(Long id) {
        int result = analysisQuestionMapper.deleteById(id);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean DeleteAnalysisQuestionList(List<Long> ids) {
        int result = analysisQuestionMapper.deleteByIds(ids);
        return result > 0;
    }
}