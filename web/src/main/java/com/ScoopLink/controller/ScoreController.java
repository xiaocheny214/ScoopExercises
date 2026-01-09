package com.ScoopLink.controller;


import com.ScoopLink.response.CommonResponse;
import com.ScoopLink.scoreCalculation.dto.Score;
import com.ScoopLink.scoreCalculation.server.ScoreServer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/score/v1")
public class ScoreController {


    @Resource
    private ScoreServer scoreServer;

    @GetMapping
    public CommonResponse<Score> getScore(@RequestParam("id") Long id){
        Score score = scoreServer.GetScore(id);
        return CommonResponse.success(score);
    }
}
