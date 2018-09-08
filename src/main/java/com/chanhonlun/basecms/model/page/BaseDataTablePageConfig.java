package com.chanhonlun.basecms.model.page;

import com.chanhonlun.basecms.model.Breadcrumb;
import com.chanhonlun.basecms.model.MenuItem;
import com.chanhonlun.basecms.model.component.BaseDataTableConfig;

import java.util.List;

public abstract class BaseDataTablePageConfig extends BaseBlankPageConfig {

    public BaseDataTableConfig datatable;

    public BaseDataTablePageConfig(List<Breadcrumb> breadcrumbs, List<MenuItem> menu, com.chanhonlun.basecms.model.component.BaseDataTableConfig datatable) {
        super(breadcrumbs, menu);
        this.datatable = datatable;
    }
}
