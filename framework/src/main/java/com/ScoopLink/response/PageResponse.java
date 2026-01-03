package com.ScoopLink.response;

import java.util.List;

public class PageResponse<T> {
    private int page;           // 当前页码
    private int size;           // 每页大小
    private long totalElements;// 总元素数量
    private int totalPages;     // 总页数
    private List<T> content;    // 当前页内容
    private boolean success;    // 请求是否成功
    private String message;     // 消息

    public PageResponse() {
        this.success = true;
        this.message = "请求成功";
    }

    public PageResponse(int page, int size, long totalElements, List<T> content) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.content = content;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
        this.success = true;
        this.message = "请求成功";
    }

    // 静态方法用于创建分页响应
    public static <T> PageResponse<T> of(int page, int size, long totalElements, List<T> content) {
        return new PageResponse<>(page, size, totalElements, content);
    }

    // 静态方法用于创建空分页响应
    public static <T> PageResponse<T> empty() {
        return new PageResponse<>(0, 0, 0L, java.util.Collections.emptyList());
    }

    // Getters and Setters
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}