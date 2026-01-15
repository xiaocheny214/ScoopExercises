package com.ScoopLink.controller;


import com.ScoopLink.dto.PageResponse;
import com.ScoopLink.response.CommonResponse;
import com.ScoopLink.scoreCalculation.dto.Score;
import com.ScoopLink.scoreCalculation.dto.ScoreInfo;
import com.ScoopLink.scoreCalculation.dto.ScoreRequest;
import com.ScoopLink.scoreCalculation.dto.UserScore;
import com.ScoopLink.scoreCalculation.server.StatisticsScore;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report/v1")
public class ReportController {

    @Resource
    private StatisticsScore statisticsScoreServer;


    /**
     * 获取分数报告
     * @param request 分页请求
     * @return 分页结果
     */
    @PostMapping("/scores")
    public CommonResponse<PageResponse<UserScore>>report(@RequestBody ScoreRequest request){
        PageResponse<UserScore> pageResponse = statisticsScoreServer.getScores(request);

        if (pageResponse != null) {
            return CommonResponse.success(pageResponse);
        }
        return CommonResponse.error("操作失败");
    }

    /**
     * 获取答题详情
     * @param scoreId 分数ID
     * @return 答题详情
     */
    @GetMapping("/scores/{scoreId}")
    public CommonResponse<ScoreInfo> getScoreInfo(@PathVariable Long scoreId){
        ScoreInfo scoreInfo = statisticsScoreServer.GetScoreInfo(scoreId);
        return CommonResponse.success(scoreInfo);
    }

    /**
     * 清除分数记录
     * @param scoreId 分数ID
     * @return 删除结果
     */
   @DeleteMapping("/scores/{scoreId}")
   public CommonResponse<Boolean> deleteScore(@PathVariable Long scoreId){
      Boolean result = statisticsScoreServer.deleteScore(scoreId);
       return CommonResponse.success(result);
   }




}
