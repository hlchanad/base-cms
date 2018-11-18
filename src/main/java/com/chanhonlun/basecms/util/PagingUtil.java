package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.request.Paging;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PagingUtil {

    private static final Logger logger = LoggerFactory.getLogger(PagingUtil.class);

    private static final Sort.Direction DEFAULT_SORTING_DIRECTION = Sort.Direction.ASC;
    private static final String         DEFAULT_SORTING_COLUMN    = "createdDt";
    private static final int            DEFAULT_PAGING_PAGE       = 0;
    private static final int            DEFAULT_PAGING_ITEMS      = 10;


    /**
     * Generate a {@code Sort} object for {@code CrudRepository} to
     * get a sorted list of records
     *
     * @param paging
     * @return Sort
     */
    private static Sort parseSorting(Paging paging) {
        return parseSorting(paging, DEFAULT_SORTING_DIRECTION, DEFAULT_SORTING_COLUMN);
    }

    /**
     * Generate a {@code Sort} object for {@code CrudRepository} to
     * get a sorted list of records
     * <p>
     * pass {@code defaultSortBy} and {@code defaultDirection} to override the global default value
     *
     * @param paging
     * @param defaultDirection
     * @param defaultSortBy
     * @return Sort
     */
    private static Sort parseSorting(Paging paging, Sort.Direction defaultDirection, String... defaultSortBy) {

        String[] sortBy = StringUtils.isNotBlank(paging.getSortBy()) ? new String[]{ paging.getSortBy() } : defaultSortBy;

        Sort.Direction sortDirection = StringUtils.isNotBlank(paging.getDirection())
                ? Sort.Direction.fromString(paging.getDirection())
                : defaultDirection;

        return new Sort(sortDirection, sortBy);
    }

    /**
     * Generate a {@code PageRequest} object for {@code CrudRepository} to
     * get a paged and sorted list of records
     *
     * @param paging
     * @return Pageable
     */
    public static Pageable parsePagination(Paging paging) {
        return parsePagination(paging, DEFAULT_SORTING_DIRECTION, DEFAULT_SORTING_COLUMN);
    }

    /**
     * Generate a {@code PageRequest} object for {@code CrudRepository} to
     * get a paged and sorted list of records
     * <p>
     * pass {@code defaultSortBy} and {@code defaultDirection} to override the global default value
     *
     * @param paging
     * @param defaultDirection
     * @param defaultSortBy
     * @return Pageable
     */
    public static Pageable parsePagination(Paging paging, Sort.Direction defaultDirection, String... defaultSortBy) {

        if (paging == null) return null;

        int page  = paging.getPage()  != null ? paging.getPage() : DEFAULT_PAGING_PAGE;
        int limit = paging.getLimit() != null ? paging.getLimit() : DEFAULT_PAGING_ITEMS;

        return PageRequest.of(page, limit, parseSorting(paging, defaultDirection, defaultSortBy));
    }
}