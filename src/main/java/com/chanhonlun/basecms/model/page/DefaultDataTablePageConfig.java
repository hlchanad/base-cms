package com.chanhonlun.basecms.model.page;

import com.chanhonlun.basecms.model.Breadcrumb;
import com.chanhonlun.basecms.model.MenuItem;
import com.chanhonlun.basecms.model.component.BaseDataTableConfig;
import lombok.Builder;

import java.util.List;

public class DefaultDataTablePageConfig extends BaseDataTablePageConfig {

    @Builder
    public DefaultDataTablePageConfig(List<Breadcrumb> breadcrumbs, List<MenuItem> menu, BaseDataTableConfig datatable) {
        super(breadcrumbs, menu, datatable);
    }
}
