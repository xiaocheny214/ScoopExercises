package com.ScoopLink.scoreCalculation.server;


import com.ScoopLink.dto.PageResponse;
import com.ScoopLink.scoreCalculation.dto.Score;
import com.ScoopLink.scoreCalculation.dto.ScoreInfo;
import com.ScoopLink.scoreCalculation.dto.ScoreRequest;
import com.ScoopLink.scoreCalculation.dto.UserScore;

public interface StatisticsScore {

    /**
     * 分页获取分数
     * @param request 分页请求
     * @return 分页结果
     */
    PageResponse<UserScore> getScores(ScoreRequest request);

    ScoreInfo GetScoreInfo(Long scoreId);

    /**
     * 删除分数记录
     * @param scoreId 分数ID
     * @return 删除结果
     */
    boolean deleteScore(Long scoreId);
}
