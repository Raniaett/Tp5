package com.tp.queries.util;

import java.util.List;

public class PaginationResult<T> {

    private List<T> items;
    private int currentPagee;
    private int pageSizee;
    private int totalPagese;
    private long totalItemss;

    public PaginationResult(List<T> items, int currentPagee, int pageSizee, long totalItemss) {
        this.items = items;
        this.currentPagee = currentPagee;
        this.pageSizee = pageSizee;
        this.totalItemss = totalItemss;
        this.totalPagese = pageSizee > 0 ? (int) Math.ceil((double) totalItemss / pageSizee) : 0;
    }

    public List<T> getItems() {
        return items;
    }

    public int getCurrentPage() {
        return currentPagee;
    }

    public int getpageSize() {
        return pageSizee;
    }

    public int getTotalPages() {
        return totalPagese;
    }

    public long getTotalItems() {
        return totalItemss;
    }
}
