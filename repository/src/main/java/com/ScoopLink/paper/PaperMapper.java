package com.ScoopLink.paper;

import com.ScoopLink.manageQuestion.papers.dto.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaperMapper {
    /**
     * 创建试卷
     */
    int insertPaper(Paper paper);

    /**
     * 根据ID获取试卷
     */
    Paper selectById(@Param("id") Long id);

    /**
     * 获取所有试卷列表
     */
    List<Paper> selectAll();

    /**
     * 更新试卷
     */
    int updatePaper(Paper paper);

    /**
     * 根据ID删除试卷
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除试卷
     */
    int deleteByIds(@Param("ids") List<Long> ids);
}