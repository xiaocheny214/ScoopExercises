package com.ScoopLink.controller;


import com.ScoopLink.response.CommonResponse;
import com.ScoopLink.scoreCalculation.dto.Score;
import com.ScoopLink.userAnswers.dto.UserAnswer;
import com.ScoopLink.userAnswers.dto.submitPaper;
import com.ScoopLink.userAnswers.server.SubmitAnswer;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Answer/v1")
public class submitAnswerController {


    @Resource
    private SubmitAnswer submitAnswer;
    /**
     * 提交答案
     */
    @PostMapping("/submit")
    public CommonResponse submitAnswer(@RequestBody UserAnswer userAnswer) {
        UserAnswer success = submitAnswer.SubmitAnswer(userAnswer);
        if (success == null) {
            return CommonResponse.error("提交失败");
        }
        return CommonResponse.success("提交成功", success);
    }

    /**
     * 提交试卷
     */
    @PostMapping("/paper")
    public CommonResponse<Score> submitPaper(@RequestBody submitPaper submitPaper) {
        Score score = submitAnswer.SubmitPaper(submitPaper);
        if (score == null) {
            return CommonResponse.error("提交失败");
        }
        return CommonResponse.success("提交成功", score);
    }
}
