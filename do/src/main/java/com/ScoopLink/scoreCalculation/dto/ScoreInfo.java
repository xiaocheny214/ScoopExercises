package com.ScoopLink.scoreCalculation.dto;


import com.ScoopLink.manageQuestion.question.dto.Question;
import lombok.Data;

import java.io.Serializable;


@Data
public class ScoreInfo implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 分数信息
     */
    private UserScore scoreInfo;

    /**
     * 用户答案
     */
    private Question userAnswer;
}
