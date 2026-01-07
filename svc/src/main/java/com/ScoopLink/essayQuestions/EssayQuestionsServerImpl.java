package com.ScoopLink.essayQuestions;

import com.ScoopLink.essayQuestion.EssayQuestionMapper;
import com.ScoopLink.manageQuestion.essayQuestions.dto.EssayQuestion;
import com.ScoopLink.manageQuestion.essayQuestions.server.EssayQuestionServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EssayQuestionsServerImpl implements EssayQuestionServer {

    @Resource
    private EssayQuestionMapper essayQuestionMapper;
    
    @Override
    @Transactional
    public boolean CreateEssayQuestion(EssayQuestion essayQuestion) {
        int result = essayQuestionMapper.insertEssayQuestion(essayQuestion);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean CreateEssayQuestions(List<EssayQuestion> essayQuestions) {
        int result = essayQuestionMapper.insertEssayQuestions(essayQuestions);
        return result > 0;
    }

    @Override
    public EssayQuestion GetEssayQuestion(Long id) {
        return essayQuestionMapper.selectById(id);
    }

    @Override
    @Transactional
    public boolean UpdateEssayQuestion(EssayQuestion essayQuestion) {
        int result = essayQuestionMapper.updateEssayQuestion(essayQuestion);
        return result > 0;
    }

    @Override
    public List<EssayQuestion> GetEssayQuestionsByPaperId(Long paperId) {
        return essayQuestionMapper.selectByPaperId(paperId);
    }

    @Override
    @Transactional
    public boolean DeleteEssayQuestion(Long id) {
        int result = essayQuestionMapper.deleteById(id);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean DeleteEssayQuestionList(List<Long> ids) {
        int result = essayQuestionMapper.deleteByIds(ids);
        return result > 0;
    }
}