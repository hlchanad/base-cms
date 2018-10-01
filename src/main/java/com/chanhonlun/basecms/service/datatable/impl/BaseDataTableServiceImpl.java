package com.chanhonlun.basecms.service.datatable.impl;

import com.chanhonlun.basecms.util.ActionButtonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

public abstract class BaseDataTableServiceImpl {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String section;

    @Value("${server.servlet.context-path}")
    protected String contextPath;

    @Autowired
    protected ActionButtonUtil actionButtonUtil;

    public void setSection(String section) {
        this.section = section;
        actionButtonUtil.init(this.section);
    }

    public String getSection() {
        return section;
    }

    public String getContextPath() {
        return contextPath;
    }
}
