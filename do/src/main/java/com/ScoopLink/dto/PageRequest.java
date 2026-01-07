package com.ScoopLink.dto;

import lombok.Data;

@Data
public class PageRequest {
    private int page = 0;      // 页码，从0开始
    private int size = 10;     // 每页大小，默认10
    
    // 静态方法用于创建分页请求
    public static PageRequest of(int page, int size) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        return pageRequest;
    }
    
    // 默认的第一页请求
    public static PageRequest defaultRequest() {
        return new PageRequest();
    }
}