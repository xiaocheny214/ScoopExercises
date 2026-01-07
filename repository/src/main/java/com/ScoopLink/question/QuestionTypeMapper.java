package com.ScoopLink.question;

import com.ScoopLink.manageQuestion.question.dto.QuestionType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionTypeMapper {
    /**
     * 插入题目类型
     */
    int insertQuestionType(QuestionType questionType);

    /**
     * 根据ID查询题目类型
     */
    QuestionType selectById(@Param("id") Long id);

    /**
     * 查询所有题目类型
     */
    List<QuestionType> selectAll();

    /**
     * 更新题目类型
     */
    int updateQuestionType(QuestionType questionType);

    /**
     * 删除题目类型
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据类型代码查询题目类型
     */
    QuestionType selectByTypeCode(@Param("typeCode") String typeCode);

    /**
     * 查询启用的题目类型
     */
    List<QuestionType> selectActiveTypes();
}