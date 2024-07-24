package com.zerobase.instamilligramapi.global.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Paging {
    private int page = 1;
    private int size = 10;

    private final static int DEFAULT_PAGE = 1;
    private final static int MAX_SIZE = 10;

    public Paging() {}

    public Paging(int page, int size) {
        this.setPage(page);
        this.setSize(size);
    }
    public void setPage(int page) {
        this.page = Math.max(DEFAULT_PAGE, page);
    }

    public void setSize(int size) {
        this.size = Math.min(MAX_SIZE, size);
        if (size == 0) {
            this.size = MAX_SIZE;
        }
    }
}
