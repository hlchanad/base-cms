package com.chanhonlun.basecms.response.page;

import com.chanhonlun.basecms.response.vo.Breadcrumb;
import com.chanhonlun.basecms.response.vo.MenuItem;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;

import java.util.List;

public abstract class BaseDataTablePageConfig extends BaseBlankPageConfig {

    public BaseDataTableConfig datatable;

    public BaseDataTablePageConfig(String pageTitle, List<Breadcrumb> breadcrumbs, List<MenuItem> menu, BaseDataTableConfig datatable) {
        super(pageTitle, breadcrumbs, menu);
        this.datatable = datatable;
    }
}
