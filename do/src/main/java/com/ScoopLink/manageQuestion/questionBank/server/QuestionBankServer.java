package com.ScoopLink.manageQuestion.questionBank.server;

import com.ScoopLink.manageQuestion.questionBank.dto.QuestionBank;
import com.ScoopLink.dto.PageRequest;
import com.ScoopLink.dto.PageResponse;

import java.util.List;

public interface QuestionBankServer {

     /**
      * 创建题库
      * @param questionBank 题库信息
      * @return 是否成功
      */
    public QuestionBank CreateQuestionBank(QuestionBank questionBank);

     /**
      * 获取题库信息
      * @param id 题库ID
      * @return 题库信息
      */
    public QuestionBank GetQuestionBank(Long id);


     /**
      * 获取题库列表
      * @return 题库列表
      */
     public List<QuestionBank> GetQuestionBankList();


     /**
      * 分页获取题库列表
      * @param pageRequest 分页请求参数
      * @return 分页题库列表
      */
     public PageResponse<QuestionBank> GetQuestionBankPage(PageRequest pageRequest);


     /**
      * 更新题库信息
      * @param questionBank 题库信息
      * @return 是否成功
      */
    public QuestionBank UpdateQuestionBank(QuestionBank questionBank);


     /**
      * 删除题库
      * @param id 题库ID
      * @return 是否成功
      */
    public boolean DeleteQuestionBank(Long id);

     /**
      * 批量删除题库
      * @param ids 题库ID列表
      * @return 是否成功
      */
    public boolean DeleteQuestionBankList(List<Long> ids);
}