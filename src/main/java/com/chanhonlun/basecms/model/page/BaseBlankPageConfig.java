package com.chanhonlun.basecms.model.page;

import com.chanhonlun.basecms.model.Breadcrumb;
import com.chanhonlun.basecms.model.MenuItem;

import java.util.List;

public abstract class BaseBlankPageConfig extends BaseConfig {

    public List<Breadcrumb> breadcrumbs;

    public List<MenuItem> menu;

    public BaseBlankPageConfig(List<Breadcrumb> breadcrumbs, List<MenuItem> menu) {
        this.breadcrumbs = breadcrumbs;
        this.menu = menu;
    }
}
