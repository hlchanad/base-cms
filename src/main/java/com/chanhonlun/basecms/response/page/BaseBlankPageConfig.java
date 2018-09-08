package com.chanhonlun.basecms.response.page;

import com.chanhonlun.basecms.response.Breadcrumb;
import com.chanhonlun.basecms.response.MenuItem;

import java.util.List;

public abstract class BaseBlankPageConfig extends BaseConfig {

    public List<Breadcrumb> breadcrumbs;

    public List<MenuItem> menu;

    public BaseBlankPageConfig(List<Breadcrumb> breadcrumbs, List<MenuItem> menu) {
        this.breadcrumbs = breadcrumbs;
        this.menu = menu;
    }
}
