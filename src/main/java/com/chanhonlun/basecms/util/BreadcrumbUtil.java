package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.model.Breadcrumb;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BreadcrumbUtil {

    private static final Logger logger = LoggerFactory.getLogger(BreadcrumbUtil.class);

    private static BreadcrumbUtil instance = null;

    private String contextPath;

    private List<Breadcrumb> base;
    private List<Breadcrumb> previous;
    private List<Breadcrumb> main;
    private List<Breadcrumb> next;

    {
        this.base = Collections.singletonList(Breadcrumb.builder()
                .title("Dashboard")
                .url("/dashboard")
                .build());
        this.previous = Collections.emptyList();
        this.main = Collections.emptyList();
        this.next = Collections.emptyList();
    }

    public static BreadcrumbUtil getInstance(String contextPath) {
        if (instance == null) {
            instance = new BreadcrumbUtil(contextPath);
        }
        return instance;
    }

    private BreadcrumbUtil(String contextPath) {
        this.contextPath = contextPath;
    }

    public BreadcrumbUtil prepend(Breadcrumb breadcrumb) {
        return prepend(Collections.singletonList(breadcrumb));
    }

    public BreadcrumbUtil prepend(List<Breadcrumb> breadcrumbs) {
        this.previous = breadcrumbs;
        return this;
    }

    public BreadcrumbUtil append(Breadcrumb breadcrumb) {
        return append(Collections.singletonList(breadcrumb));
    }

    public BreadcrumbUtil append(List<Breadcrumb> breadcrumbs) {
        this.next = breadcrumbs;
        return this;
    }

    public BreadcrumbUtil setPath(String uri) {
        uri = uri.substring(contextPath.length() + 1);
        this.main = Stream.of(uri.split("/"))
                .map(section -> Breadcrumb.builder()
                        .title(StringUtils.capitalize(section.replaceAll("-", " ")))
                        .url("/" + section)
                        .build()).collect(Collectors.toList());
        return this;
    }

    public List<Breadcrumb> getBreadcrumbs() {
        ArrayList<Breadcrumb> breadcrumbs = new ArrayList<>();

        breadcrumbs.addAll(this.base);
        breadcrumbs.addAll(this.previous);
        breadcrumbs.addAll(this.main);
        breadcrumbs.addAll(this.next);

        return appendContextPathToUrl(breadcrumbs);
    }

    private List<Breadcrumb> appendContextPathToUrl(List<Breadcrumb> breadcrumbs) {
        breadcrumbs.forEach(breadcrumb -> {
            if (StringUtils.isNoneBlank(breadcrumb.getUrl())) {
                breadcrumb.setUrl(this.contextPath + breadcrumb.getUrl());
            }
        });
        return breadcrumbs;
    }
}
