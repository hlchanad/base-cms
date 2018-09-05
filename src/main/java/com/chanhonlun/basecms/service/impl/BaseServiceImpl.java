package com.chanhonlun.basecms.service.impl;

import com.chanhonlun.basecms.service.BaseService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

public class BaseServiceImpl implements BaseService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${server.servlet.context-path}")
    protected String contextPath;

    @Autowired
    protected HttpServletRequest httpServletRequest;

    @Autowired
    protected BreadcrumbUtil breadcrumbUtil;
}
