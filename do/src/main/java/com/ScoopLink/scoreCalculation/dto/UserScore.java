package com.ScoopLink.scoreCalculation.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserScore implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分数
     */
    private Score score;

    /**
     * 题库名称
     */
    private String bankName;

    /**
     * 试卷名称
     */
    private String paperName;
}
