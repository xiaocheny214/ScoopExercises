package com.ScoopLink.manageQuestion.questionBank.server;

import com.ScoopLink.manageQuestion.questionBank.dto.QuestionBank;

import java.util.List;

public interface QuestionBankServer {

     /**
      * 创建题库
      * @param questionBank 题库信息
      * @return 是否成功
      */
    public boolean CreateQuestionBank(QuestionBank questionBank);

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
      * 更新题库信息
      * @param questionBankServer 题库信息
      * @return 是否成功
      */
    public boolean UpdateQuestionBank(QuestionBank questionBankServer);


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
