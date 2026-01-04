package com.ScoopLink.manageQuestion.multipleChoiceQuestion.server;

import com.ScoopLink.manageQuestion.multipleChoiceQuestion.dto.MultipleChoiceQuestion;

import java.util.List;

public interface MultipleChoiceQuestionServer {

     /**
      * 创建多选题
      * @param multipleChoiceQuestion 多选题信息
      * @return 是否成功
      */
    public boolean CreateMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion);


    /**
     * 批量创建多选题
     * @param multipleChoiceQuestions 多选题列表
     * @return 是否成功
     */
    public boolean CreateMultipleChoiceQuestions(List<MultipleChoiceQuestion> multipleChoiceQuestions);


    /**
      * 获取多选题信息
      * @param id 多选题ID
      * @return 多选题信息
      */
    public MultipleChoiceQuestion GetMultipleChoiceQuestion(Long id);


     /**
      * 更新多选题
      * @param multipleChoiceQuestion 多选题信息
      * @return 是否成功
      */
    public boolean UpdateMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion);



     /**
      * 删除多选题
      * @param id 多选题ID
      * @return 是否成功
      */
    public boolean DeleteMultipleChoiceQuestion(Long id);



     /**
      * 批量删除多选题
      * @param ids 多选题ID列表
      * @return 是否成功
      */
    public boolean DeleteMultipleChoiceQuestionList(List<Long> ids);



}
