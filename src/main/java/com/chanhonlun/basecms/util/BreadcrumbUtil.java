package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.model.Breadcrumb;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BreadcrumbUtil {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private List<Breadcrumb> getInitBreadcrumbs() {
        return new ArrayList<>(Arrays.asList(
                Breadcrumb.builder()
                        .title("Dashboard")
                        .url("/dashboard")
                        .build()
        ));
    }

    public List<Breadcrumb> getBreadcrumbs(List<Breadcrumb> breadcrumbs) {

        List<Breadcrumb> output = getInitBreadcrumbs();
        output.addAll(breadcrumbs);

        output.forEach(breadcrumb -> {
            if (StringUtils.isNotBlank(breadcrumb.getUrl())) {
                breadcrumb.setUrl(contextPath + breadcrumb.getUrl());
            }
        });

        return output;
    }
}
