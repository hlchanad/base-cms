package com.chanhonlun.basecms.response.page;

import com.chanhonlun.basecms.response.Breadcrumb;
import com.chanhonlun.basecms.response.MenuItem;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;

import java.util.List;

public abstract class BaseDataTablePageConfig extends BaseBlankPageConfig {

    public BaseDataTableConfig datatable;

    public BaseDataTablePageConfig(List<Breadcrumb> breadcrumbs, List<MenuItem> menu, com.chanhonlun.basecms.response.component.BaseDataTableConfig datatable) {
        super(breadcrumbs, menu);
        this.datatable = datatable;
    }
}
