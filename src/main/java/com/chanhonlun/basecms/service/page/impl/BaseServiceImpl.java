package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

public class BaseServiceImpl {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${server.servlet.context-path}")
    protected String contextPath;

    @Autowired
    protected HttpServletRequest httpServletRequest;

    @Autowired
    protected BreadcrumbUtil breadcrumbUtil;

    @Autowired
    protected SidebarMenuUtil sidebarMenuUtil;
}
