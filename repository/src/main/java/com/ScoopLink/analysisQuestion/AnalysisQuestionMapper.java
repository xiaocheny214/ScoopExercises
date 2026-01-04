package com.ScoopLink.analysisQuestion;

import com.ScoopLink.manageQuestion.analysisQuestion.dto.AnalysisQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnalysisQuestionMapper {
    /**
     * 创建单个辨析题
     */
    int insertAnalysisQuestion(AnalysisQuestion analysisQuestion);

    /**
     * 批量创建辨析题
     */
    int insertAnalysisQuestions(@Param("analysisQuestions") List<AnalysisQuestion> analysisQuestions);

    /**
     * 根据ID获取辨析题
     */
    AnalysisQuestion selectById(@Param("id") Long id);

    /**
     * 更新辨析题
     */
    int updateAnalysisQuestion(AnalysisQuestion analysisQuestion);

    /**
     * 根据ID删除辨析题
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除辨析题
     */
    int deleteByIds(@Param("ids") List<Long> ids);
}