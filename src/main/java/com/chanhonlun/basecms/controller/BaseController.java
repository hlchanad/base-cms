package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.service.page.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

public abstract class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String section;

    public String getSection() {
        if (!this.getClass().isAnnotationPresent(RequestMapping.class)) {
            throw new IllegalArgumentException("cannot auto find section path");
        }

        RequestMapping requestMapping = this.getClass().getAnnotation(RequestMapping.class);
        String[]       paths          = requestMapping.value();

        if (paths.length != 1) {
            throw new IllegalArgumentException("cannot auto find section path");
        }

        String path = paths[0].trim();
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        return path;
    }

    protected abstract BaseService getService();

    @PostConstruct
    protected void init() {
        this.section = getSection();
        getService().setSection(section);
    }
}
