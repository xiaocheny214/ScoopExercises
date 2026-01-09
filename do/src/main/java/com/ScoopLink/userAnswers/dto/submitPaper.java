package com.ScoopLink.userAnswers.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class submitPaper implements Serializable {
    private static final long serialVersionUID = 1L;


    private Integer userId;

    private Long paperId;

    private List<Long>answerIds;


}
