package com.ScoopLink.score;


import com.ScoopLink.scoreCalculation.dto.Score;
import com.ScoopLink.scoreCalculation.server.ScoreServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScoreServerImpl implements ScoreServer {

    @Resource
    private ScoreMapper scoreMapper;
    
    @Override
    @Transactional
    public boolean CreateScore(Score score) {
        int result = scoreMapper.insertScore(score);
        return result > 0;
    }

    @Override
    public Score GetScore(Long id) {
        return scoreMapper.selectById(id);
    }

    @Override
    public List<Score> GetScoreList() {
        return scoreMapper.selectAll();
    }

    @Override
    @Transactional
    public boolean UpdateScore(Score score) {
        int result = scoreMapper.updateScore(score);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean DeleteScore(Long id) {
        int result = scoreMapper.deleteById(id);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean DeleteScoreList(List<Long> ids) {
        int result = scoreMapper.deleteByIds(ids);
        return result > 0;
    }

    @Override
    public Score GetScoreByPaperId(Long paperId) {
        return scoreMapper.selectByPaperId(paperId);
    }
}