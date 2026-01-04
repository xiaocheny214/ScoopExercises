package com.ScoopLink.score;

import com.ScoopLink.scoreCalculation.dto.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScoreMapper {
    /**
     * 创建分数记录
     */
    int insertScore(Score score);

    /**
     * 根据ID获取分数记录
     */
    Score selectById(@Param("id") Long id);

    /**
     * 获取所有分数记录列表
     */
    List<Score> selectAll();

    /**
     * 更新分数记录
     */
    int updateScore(Score score);

    /**
     * 根据ID删除分数记录
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除分数记录
     */
    int deleteByIds(@Param("ids") List<Long> ids);
}