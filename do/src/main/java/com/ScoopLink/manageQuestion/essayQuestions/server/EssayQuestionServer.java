package com.ScoopLink.manageQuestion.essayQuestions.server;

import com.ScoopLink.manageQuestion.essayQuestions.dto.EssayQuestion;

import java.util.List;

public interface EssayQuestionServer {

     /**
      * 创建问答题
      * @param essayQuestion 问答题信息
      * @return 是否成功
      */
    public boolean CreateEssayQuestion(EssayQuestion essayQuestion);

    /**
     * 批量创建问答题
     * @param essayQuestions 问答题列表
     * @return 是否成功
     */
    public boolean CreateEssayQuestions(List<EssayQuestion> essayQuestions);
     /**
      * 获取问答题信息
      * @param id 问答题ID
      * @return 问答题信息
      */
    public EssayQuestion GetEssayQuestion(Long id);


     /**
      * 更新问答题
      * @param essayQuestion 问答题信息
      * @return 是否成功
      */
    public boolean UpdateEssayQuestion(EssayQuestion essayQuestion);


     /**
      * 删除问答题
      * @param id 问答题ID
      * @return 是否成功
      */
    public boolean DeleteEssayQuestion(Long id);


    /**
     * 批量删除问答题
     * @param ids 问答题ID列表
     * @return 是否成功
     */
    public boolean DeleteEssayQuestionList(List<Long> ids);
}