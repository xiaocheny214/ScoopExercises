package com.ScoopLink.user;

import com.ScoopLink.userAnswers.dto.UserAnswer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserAnswerMapper {
    /**
     * 创建用户答题记录
     */
    int insertUserAnswer(UserAnswer userAnswer);

    /**
     * 根据ID获取用户答题记录
     */
    UserAnswer selectById(@Param("id") Long id);

    /**
     * 获取所有用户答题记录列表
     */
    List<UserAnswer> selectAll();

    /**
     * 更新用户答题记录
     */
    int updateUserAnswer(UserAnswer userAnswer);

    /**
     * 根据ID删除用户答题记录
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除用户答题记录
     */
    int deleteByIds(@Param("ids") List<Long> ids);
    /**
     * 批量创建用户答题记录
     */
    int insertUserAnswers(List<UserAnswer> userAnswerList);
    /**
     * 根据问题ID获取用户答题记录
     */
    UserAnswer selectByQuestionId(Integer questionId);
}