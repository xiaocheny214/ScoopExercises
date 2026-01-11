package com.ScoopLink.userAnswers.server;

import com.ScoopLink.userAnswers.dto.UserAnswer;

import java.util.List;

public interface UserAnswersServer {

     /**
      * 创建用户答案
      * @param userAnswer 用户答案
      * @return 是否创建成功
      */
     public boolean CreateUserAnswers(UserAnswer userAnswer);


     /**
      * 批量创建用户答案
      * @param userAnswerList 用户答案列表
      * @return 是否创建成功
      */
     public boolean CreateUserAnswers(List<UserAnswer> userAnswerList);


     /**
      * 获取用户答案
      * @param id 用户答案ID
      * @return 用户答案
      */
     public UserAnswer GetUserAnswer(Long id);


     /**
      * 获取用户答案列表
      * @param questionId 问题ID
      * @return 用户答案列表
      */
     public UserAnswer GetUserAnswerByQuestionId(Integer questionId );

     /**
      * 获取用户答案列表
      * @return 用户答案列表
      */
      public List<UserAnswer> GetUserAnswerList();

      /**
       * 更新用户答案
       * @param userAnswer 用户答案
       * @return 是否更新成功
       */
      public boolean UpdateUserAnswer(UserAnswer userAnswer);

      /**
       * 删除用户答案
       * @param id 用户答案ID
       * @return 是否删除成功
       */
      public boolean DeleteUserAnswer(Long id);

      /**
       * 批量删除用户答案
       * @param ids 用户答案ID列表
       * @return 是否删除成功
       */
      public boolean DeleteUserAnswerList(List<Long> ids);

      /**
       * 根据用户ID和试卷ID获取用户答案列表
       * @param userId 用户ID
       * @param paperId 试卷ID
       * @return 用户答案列表
       */
      public List<UserAnswer> GetUserAnswerByUserIdAndPaperId(Long userId, Long paperId);
}
