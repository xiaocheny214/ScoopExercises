package com.ScoopLink.controller;



import com.ScoopLink.manageQuestion.question.dto.QuestionRequest;
import com.ScoopLink.manageQuestion.question.dto.QuestionResponse;
import com.ScoopLink.manageQuestion.question.dto.QuestionType;
import com.ScoopLink.manageQuestion.question.server.QuestionTypeServer;
import com.ScoopLink.manageQuestion.question.server.QuestionServer;
import com.ScoopLink.response.CommonResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/question/v1")
@RestController
@Slf4j
public class QuestionController {

    @Resource
    private QuestionTypeServer questionTypeServer;
    @Resource
    private QuestionServer questionServer;


    /**
     * 获取所有题目类型
     */
    @GetMapping("/types")
    public CommonResponse<List<QuestionType>> getQuestionTypes(){
        log.info("获取所有题目类型");
        List<QuestionType> questionTypes = questionTypeServer.GetAllQuestionTypes();
        return CommonResponse.success(questionTypes);
    }


    /**
     * 创建题目
     */
    @PostMapping("/create")
    public CommonResponse<QuestionResponse> createQuestion(@RequestBody QuestionRequest request) {
        log.info("创建题目: {}", request);
        QuestionResponse response = questionServer.createQuestion(request);
        return response.getSuccess() ?
                CommonResponse.success(response) :
                CommonResponse.error(response.getErrorMessage());
    }

    /**
     * 批量创建题目
     */
    @PostMapping("/create/batch")
    public CommonResponse<List<QuestionResponse>> createQuestions(@RequestBody List<QuestionRequest> requests) {
        log.info("批量创建题目: {}", requests);
        List<QuestionResponse> responses = questionServer.createQuestions(requests);
        return CommonResponse.success(responses);
    }

    /**
     * 根据ID获取题目
     */
    @GetMapping("/get/{id}")
    public CommonResponse<QuestionResponse> getQuestion(
            @PathVariable Long id,
            @RequestParam Long questionTypeId) {
        log.info("根据ID获取题目: id={}, questionTypeId={}", id, questionTypeId);
        QuestionResponse response = questionServer.getQuestion(id, questionTypeId);
        return response.getSuccess() ?
                CommonResponse.success(response) :
                CommonResponse.error(response.getErrorMessage());
    }

    /**
     * 根据试卷ID获取题目列表
     */
    @GetMapping("/list/paper/{paperId}")
    public CommonResponse<List<QuestionResponse>> getQuestionsByPaperId(@PathVariable Long paperId) {
        log.info("根据试卷ID获取题目列表: paperId={}", paperId);
        List<QuestionResponse> responses = questionServer.getQuestionsByPaperId(paperId);
        return CommonResponse.success(responses);
    }

    /**
     * 根据题目类型获取题目列表
     */
    @GetMapping("/list/type/{questionTypeId}")
    public CommonResponse<List<QuestionResponse>> getQuestionsByType(@PathVariable Long questionTypeId) {
        log.info("根据题目类型获取题目列表: questionTypeId={}", questionTypeId);
        List<QuestionResponse> responses = questionServer.getQuestionsByType(questionTypeId);
        return CommonResponse.success(responses);
    }

    /**
     * 更新题目
     */
    @PutMapping("/update")
    public CommonResponse<QuestionResponse> updateQuestion(@RequestBody QuestionRequest request) {
        log.info("更新题目: {}", request);
        QuestionResponse response = questionServer.updateQuestion(request);
        return response.getSuccess() ?
                CommonResponse.success(response) :
                CommonResponse.error(response.getErrorMessage());
    }

    /**
     * 删除题目
     */
    @DeleteMapping("/delete/{id}")
    public CommonResponse<QuestionResponse> deleteQuestion(
            @PathVariable Long id,
            @RequestParam Long questionTypeId) {
        log.info("删除题目: id={}, questionTypeId={}", id, questionTypeId);
        QuestionResponse response = questionServer.deleteQuestion(id, questionTypeId);
        return response.getSuccess() ?
                CommonResponse.success(response) :
                CommonResponse.error(response.getErrorMessage());
    }

    /**
     * 批量删除题目
     */
    @PostMapping("/delete/batch")
    public CommonResponse<List<QuestionResponse>> deleteQuestions(
            @RequestBody List<Long> ids,
            @RequestParam Long questionTypeId) {
        log.info("批量删除题目: ids={}, questionTypeId={}", ids, questionTypeId);
        List<QuestionResponse> responses = questionServer.deleteQuestions(ids, questionTypeId);
        return CommonResponse.success(responses);
    }
}
