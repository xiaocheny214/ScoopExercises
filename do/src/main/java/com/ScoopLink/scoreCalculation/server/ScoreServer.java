package com.ScoopLink.scoreCalculation.server;

import com.ScoopLink.scoreCalculation.dto.Score;

import java.util.List;

public interface ScoreServer {


    public boolean CreateScore(Score score);


    public Score GetScore(Long id);


    public List<Score> GetScoreList();


    public boolean UpdateScore(Score score);


    public boolean DeleteScore(Long id);


    public boolean DeleteScoreList(List<Long> ids);
}
