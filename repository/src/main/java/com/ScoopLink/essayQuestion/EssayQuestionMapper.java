package com.ScoopLink.essayQuestion;

import com.ScoopLink.manageQuestion.essayQuestions.dto.EssayQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EssayQuestionMapper {
    /**
     * 创建单个解答题
     */
    int insertEssayQuestion(EssayQuestion essayQuestion);

    /**
     * 批量创建解答题
     */
    int insertEssayQuestions(@Param("essayQuestions") List<EssayQuestion> essayQuestions);

    /**
     * 根据ID获取解答题
     */
    EssayQuestion selectById(@Param("id") Long id);
    /**
     * 根据试卷ID获取解答题列表
     */
    List<EssayQuestion> selectByPaperId(@Param("paperId") Long paperId);

    /**
     * 更新解答题
     */
    int updateEssayQuestion(EssayQuestion essayQuestion);

    /**
     * 根据ID删除解答题
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除解答题
     */
    int deleteByIds(@Param("ids") List<Long> ids);
}