package com.nandina.api.models;


import lombok.Getter;

import java.util.List;

@Getter
public class PagedContent<T> {
    private final List<T> content;
    private final int currentPage, pageSize, totalCount;

    public PagedContent(List<T> content, int currentPage, int pageSize, int totalCount) {
        this.content = content;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return (totalCount + pageSize -1) / pageSize;
    }
}

