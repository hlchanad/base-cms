package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.service.page.BasePageService;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDataTable;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BasePageServiceImpl implements BasePageService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${server.servlet.context-path}")
    protected String contextPath;

    @Autowired
    protected HttpServletRequest httpServletRequest;

    @Autowired
    protected BreadcrumbUtil breadcrumbUtil;

    @Autowired
    protected SidebarMenuUtil sidebarMenuUtil;

    protected String section;

    public Logger getLogger() {
        return logger;
    }

    @Override
    public void setSection(String section) {
        this.section = section;

        if (this instanceof DefaultServiceHasDataTable) {
            ((DefaultServiceHasDataTable) this).getDataTablesService().setSection(this.section);
        }
    }

    @Override
    public String getSection(){
        return this.section;
    }

    @Override
    public String getPageTitle() {
        return Stream.of(getSection().split("-"))
                .map(StringUtils::capitalize)
                .collect(Collectors.joining(" "));
    }

    @Override
    public String getContextPath() {
        return this.contextPath;
    }

}
