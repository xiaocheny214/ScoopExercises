package com.ScoopLink.multipleChoiceQuestion;

import com.ScoopLink.manageQuestion.multipleChoiceQuestion.dto.MultipleChoiceQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MultipleChoiceQuestionMapper {
    /**
     * 创建单个选择题
     */
    int insertMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion);

    /**
     * 批量创建选择题
     */
    int insertMultipleChoiceQuestions(@Param("multipleChoiceQuestions") List<MultipleChoiceQuestion> multipleChoiceQuestions);

    /**
     * 根据ID获取选择题
     */
    MultipleChoiceQuestion selectById(@Param("id") Long id);

    /**
     * 更新选择题
     */
    int updateMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion);

    /**
     * 根据ID删除选择题
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除选择题
     */
    int deleteByIds(@Param("ids") List<Long> ids);
}