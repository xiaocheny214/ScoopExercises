package com.ScoopLink.controller;


import com.ScoopLink.dto.PageRequest;
import com.ScoopLink.dto.PageResponse;
import com.ScoopLink.manageQuestion.questionBank.dto.QuestionBank;
import com.ScoopLink.manageQuestion.questionBank.server.QuestionBankServer;
import com.ScoopLink.response.CommonResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank/v1")
public class BankController {

    @Resource
    private QuestionBankServer questionBankServer;

    /**
     * 创建题库
     * @param questionBank 题库信息
     * @return 是否成功
     */
    @PostMapping("/init")
    public CommonResponse<QuestionBank> CreateQuestionBank(@RequestBody QuestionBank questionBank){
        QuestionBank bank = questionBankServer.CreateQuestionBank(questionBank);
        return CommonResponse.success(bank);
    }

    /**
     * 根据ID获取题库
     * @param id 题库ID
     * @return 题库信息
     */
    @GetMapping("/get/{id}")
    public CommonResponse<QuestionBank> getQuestionBank(@PathVariable Long id){
        QuestionBank bank = questionBankServer.GetQuestionBank(id);
        return CommonResponse.success(bank);
    }

    /**
     * 获取题库列表（不分页）
     * @return 题库列表
     */
    @GetMapping("/list")
    public CommonResponse<List<QuestionBank>> getQuestionBankList(){
        List<QuestionBank> banks = questionBankServer.GetQuestionBankList();
        return CommonResponse.success(banks);
    }

    /**
     * 分页获取题库列表
     * @param page 页码
     * @param size 每页大小
     * @return 分页题库列表
     */
    @GetMapping("/page")
    public CommonResponse<PageResponse<QuestionBank>> getQuestionBankPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        PageResponse<QuestionBank> response = questionBankServer.GetQuestionBankPage(pageRequest);
        return CommonResponse.success(response);
    }

    /**
     * 更新题库信息
     * @param questionBank 题库信息
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResponse<Boolean> updateQuestionBank(@RequestBody QuestionBank questionBank){
        boolean success = questionBankServer.UpdateQuestionBank(questionBank);
        return CommonResponse.success(success);
    }

    /**
     * 删除题库
     * @param id 题库ID
     * @return 是否成功
     */
    @DeleteMapping("/delete/{id}")
    public CommonResponse<Boolean> deleteQuestionBank(@PathVariable Long id){
        boolean success = questionBankServer.DeleteQuestionBank(id);
        return CommonResponse.success(success);
    }

    /**
     * 批量删除题库
     * @param ids 题库ID列表
     * @return 是否成功
     */
    @PostMapping("/delete/batch")
    public CommonResponse<Boolean> deleteQuestionBankList(@RequestBody List<Long> ids){
        boolean success = questionBankServer.DeleteQuestionBankList(ids);
        return CommonResponse.success(success);
    }

}