package com.ScoopLink.paper;

import com.ScoopLink.manageQuestion.papers.dto.Paper;
import com.ScoopLink.manageQuestion.papers.server.PaperServer;
import com.ScoopLink.manageQuestion.questionBank.dto.QuestionBank;
import com.ScoopLink.manageQuestion.questionBank.server.QuestionBankServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaperServerImpl implements PaperServer {

    @Resource
    private PaperMapper paperMapper;
    @Resource
    private QuestionBankServer questionBankServer;
    
    @Override
    @Transactional(noRollbackFor = IllegalArgumentException.class)
    public Paper CreatePaper(Paper paper) {
        paper.setCreateTime(LocalDateTime.now());
        //查询一下试卷库是否存在
        QuestionBank questionBank = questionBankServer.GetQuestionBank(paper.getBankId());
        if (questionBank == null) {
            throw new IllegalArgumentException("试卷库不存在");
        }
        int result = paperMapper.insertPaper(paper);
        return result > 0 ? paper : null;
    }

    @Override
    public Paper GetPaper(Long id) {
        return paperMapper.selectById(id);
    }

    @Override
    public List<Paper> GetPaperList() {
        return paperMapper.selectAll();
    }

    @Override
    @Transactional(noRollbackFor = IllegalArgumentException.class)
    public Paper UpdatePaper(Paper paper) {
        //查询一下试卷库是否存在
        QuestionBank questionBank = questionBankServer.GetQuestionBank(paper.getBankId());
        if (questionBank == null) {
            throw new IllegalArgumentException("试卷库不存在");
        }
        //查询一下试卷是否存在
        Paper oldPaper = paperMapper.selectById(paper.getId());
        if (oldPaper == null) {
            throw new IllegalArgumentException("试卷不存在");
        }
        paper.setCreateTime(oldPaper.getCreateTime());
        paper.setTotalScore(oldPaper.getTotalScore());
        paper.setQuestionCount(oldPaper.getQuestionCount());
        int result = paperMapper.updatePaper(paper);
        return result > 0 ? paper : null;
    }

    @Override
    @Transactional
    public boolean DeletePaper(Long id) {
        int result = paperMapper.deleteById(id);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean DeletePaperList(List<Long> ids) {
        int result = paperMapper.deleteByIds(ids);
        return result > 0;
    }
}