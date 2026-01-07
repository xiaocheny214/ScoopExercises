package com.ScoopLink.manageQuestion.multipleChoiceQuestion.dto;

import lombok.Data;

@Data
public class MultipleChoiceQuestion {

    private Long id;

     /**
     * 试卷ID
     */
    private Long paperId;

     /**
     * 问题文本
     */
    private String questionText;

     /**
     * 选项A
     */
    private String optionA;

     /**
     * 选项B
     */
    private String optionB;

     /**
     * 选项C
     */
    private String optionC;

     /**
     * 选项D
     */
    private String optionD;

     /**
     * 选项E
     */
    private String optionE;

     /**
     * 选项F
     */
    private String optionF;

     /**
     * 正确答案
     */
    private String correctAnswer;

     /**
     * 解释说明
     */
    private String explanation;

     /**
     * 分数
     */
    private Integer score;

     /**
     * 排序顺序
     */
    private Integer sortOrder;

     /**
     * 题目类型ID
     */
    private Integer questionTypeId;
}
