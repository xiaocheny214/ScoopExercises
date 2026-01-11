package com.ScoopLink.scoreCalculation.server;

import com.ScoopLink.dto.PageResponse;
import com.ScoopLink.scoreCalculation.dto.Score;
import com.ScoopLink.scoreCalculation.dto.ScoreInfo;
import com.ScoopLink.scoreCalculation.dto.ScoreRequest;

import java.util.List;

public interface ScoreServer {


    public boolean CreateScore(Score score);


    public Score GetScore(Long id);


    public List<Score> GetScoreList();


    public boolean UpdateScore(Score score);


    public boolean DeleteScore(Long id);


    public boolean DeleteScoreList(List<Long> ids);

    /**
     * 根据试卷ID获取分数
     * @param paperId 试卷ID
     * @return 分数
     */
    Score GetScoreByPaperId(Long paperId);
}
