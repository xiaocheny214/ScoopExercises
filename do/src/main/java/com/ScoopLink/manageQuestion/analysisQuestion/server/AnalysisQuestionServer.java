package com.ScoopLink.manageQuestion.analysisQuestion.server;

import com.ScoopLink.manageQuestion.analysisQuestion.dto.AnalysisQuestion;

import java.util.List;

public interface AnalysisQuestionServer {
     /**
      * 创建分析题
      * @param analysisQuestion 分析题信息
      * @return 是否成功
      */
    public boolean CreateAnalysisQuestion(AnalysisQuestion analysisQuestion);

    /**
     * 批量创建分析题
     * @param analysisQuestions 分析题列表
     * @return 是否成功
     */
    public boolean CreateAnalysisQuestions(List<AnalysisQuestion> analysisQuestions);
      /**
       * 获取分析题信息
       * @param id 分析题ID
       * @return 分析题信息
       */
    public AnalysisQuestion GetAnalysisQuestion(Long id);


      /**
       * 更新分析题
       * @param analysisQuestion 分析题信息
       * @return 是否成功
       */
    public boolean UpdateAnalysisQuestion(AnalysisQuestion analysisQuestion);


      /**
       * 删除分析题
       * @param id 分析题ID
       * @return 是否成功
       */
    public boolean DeleteAnalysisQuestion(Long id);


      /**
       * 批量删除分析题
       * @param ids 分析题ID列表
       * @return 是否成功
       */
    public boolean DeleteAnalysisQuestionList(List<Long> ids);





}
