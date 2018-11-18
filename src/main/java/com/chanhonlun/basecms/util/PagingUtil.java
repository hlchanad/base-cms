package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.request.Paging;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * transform simple {@code Pageable} to {@code Paging}
     *
     * @param pageable
     * @return Paging
     */
    public static Paging getPaging(Pageable pageable) {
        Sort.Order order = pageable.getSort().iterator().next();

        return Paging.builder()
                .page(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .direction(order.getDirection().name())
                .sortBy(order.getProperty())
                .build();
    }

    /**
     * get hateoas links about self, previous, next, first, last
     *
     * @param page result fetched from repository
     * @param pageable original {@code Pageable} request
     * @return map of rel -> pageable
     */
    public static Map<String, Pageable> getHateoasPageable(Page<?> page, Pageable pageable) {

        Paging lastPaging = getPaging(pageable);
        lastPaging.setPage(page.getTotalPages() - 1);

        boolean hasNextPage = pageable.getPageNumber() < page.getTotalPages() - 1;

        Map<String, Pageable> map = new HashMap<>();

        map.put("self", pageable);
        map.put("previous", pageable.hasPrevious() ? pageable.previousOrFirst() : null);
        map.put("next", hasNextPage ? pageable.next() : null);
        map.put("first", pageable.first());
        map.put("last", parsePagination(lastPaging));

        return map;
    }
}