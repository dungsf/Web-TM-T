package com.rikkeisoft.canifashop.base;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Builder
@Getter
public final class BasePagerData<T> {
    private final List<T> content;
    private final Integer page;
    private final Integer size;
    private final Integer totalPages;
    private final Long totalElements;

    public static <T> BasePagerData<T> buildEmpty(Pageable pageable) {
        return BasePagerData
                .<T>builder()
                .content(Collections.emptyList())
                .totalElements(0L)
                .totalPages(0)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();
    }

    public static <T> BasePagerData<T> build(Page<T> page) {
        return BasePagerData
                .<T>builder()
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .content(page.getContent())
                .build();
    }

    public static <T> BasePagerData<T> build(Page<T> page, List<T> content) {
        return BasePagerData
                .<T>builder()
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .content(content)
                .build();
    }

    public Integer getTotalPages() {
        if (size  != null && totalElements != null) {
            return size == 0 ? 1 : (int) Math.ceil((double) totalElements / (double) size);
        }
        return totalPages;
    }

    public <R> BasePagerData<R> map(Function<T, R> mapFunc) {
        List<R> newContent = new ArrayList<>();
        if (!content.isEmpty()) {
            try {
                newContent.addAll(content.stream().map(mapFunc).collect(Collectors.toList()));
            } catch (Exception ignored) {

            }
        }

        return BasePagerData
                .<R>builder()
                .content(newContent)
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(getTotalPages())
                .build();
    }
}