package com.nandina.api.forms;


import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageForm {

    @Min(value = 1)
    private Integer page;

    @Min(value = 1)
    private Integer size;

    public int getPageOrDefault() {
        return page == null ? 1 : page;
    }

    public int getSizeOrDefault() {
        return size == null ? 10 : size;
    }

    public int getSizeOrDefault(int defaultValue) {
        return size == null ? defaultValue : size;
    }
}

