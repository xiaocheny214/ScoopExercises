package com.ScoopLink.questionBankMapper;

import com.ScoopLink.manageQuestion.questionBank.dto.QuestionBank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionBankMapper {
    /**
     * 创建题库
     */
    int insertQuestionBank(QuestionBank questionBank);

    /**
     * 根据ID获取题库
     */
    QuestionBank selectById(@Param("id") Long id);

    /**
     * 获取所有题库列表
     */
    List<QuestionBank> selectAll();

    /**
     * 分页查询题库
     */
    List<QuestionBank> selectWithPaging(@Param("offset") int offset, @Param("size") int size);

    /**
     * 获取题库总数
     */
    int countAll();

    /**
     * 更新题库
     */
    int updateQuestionBank(QuestionBank questionBank);

    /**
     * 根据ID删除题库
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除题库
     */
    int deleteByIds(@Param("ids") List<Long> ids);
}