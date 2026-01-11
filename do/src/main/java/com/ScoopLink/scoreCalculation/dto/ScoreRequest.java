package com.ScoopLink.scoreCalculation.dto;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ScoreRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long paperId;

    private Long bankId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer size;

    private Integer page;

    private String orderBy;

    private String sortOrder;
}
