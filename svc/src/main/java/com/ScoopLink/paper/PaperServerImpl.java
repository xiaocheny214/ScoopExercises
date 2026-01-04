package com.ScoopLink.paper;

import com.ScoopLink.manageQuestion.papers.dto.Paper;
import com.ScoopLink.manageQuestion.papers.server.PaperServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaperServerImpl implements PaperServer {

    @Resource
    private PaperMapper paperMapper;
    
    @Override
    @Transactional
    public boolean CreatePaper(Paper paper) {
        int result = paperMapper.insertPaper(paper);
        return result > 0;
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
    @Transactional
    public boolean UpdatePaper(Paper paper) {
        int result = paperMapper.updatePaper(paper);
        return result > 0;
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