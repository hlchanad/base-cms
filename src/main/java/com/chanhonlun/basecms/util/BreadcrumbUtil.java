package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.response.Breadcrumb;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BreadcrumbUtil {

    private static final Logger logger = LoggerFactory.getLogger(BreadcrumbUtil.class);

    private static final List<String> excludeUris = Arrays.asList("dashboard");

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private List<Breadcrumb> base;
    private List<Breadcrumb> previous;
    private List<Breadcrumb> main;
    private List<Breadcrumb> next;

    @PostConstruct
    public void init() {
        this.base = Collections.singletonList(Breadcrumb.builder()
                .title("Dashboard")
                .url("/dashboard")
                .build());

        String uri = httpServletRequest.getRequestURI().substring(contextPath.length() + 1);

        List<String> pathFragments = Stream.of(uri.split("/"))
                .filter(section -> !excludeUris.contains(section))
                .collect(Collectors.toList());

        this.main = new ArrayList<>();
        for (int i = 0; i < pathFragments.size(); i ++) {
            String section = pathFragments.get(i);
            String url = pathFragments.subList(0, i + 1).stream().collect(Collectors.joining("/"));

            this.main.add(Breadcrumb.builder()
                    .title(StringUtils.capitalize(section.replaceAll("-", " ")))
                    .url("/" + url)
                    .build());
        }

        this.previous = Collections.emptyList();
        this.next = Collections.emptyList();
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
