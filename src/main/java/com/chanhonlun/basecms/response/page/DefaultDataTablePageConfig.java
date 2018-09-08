package com.chanhonlun.basecms.response.page;

import com.chanhonlun.basecms.response.Breadcrumb;
import com.chanhonlun.basecms.response.MenuItem;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import lombok.Builder;

import java.util.List;

public class DefaultDataTablePageConfig extends BaseDataTablePageConfig {

    @Builder
    public DefaultDataTablePageConfig(List<Breadcrumb> breadcrumbs, List<MenuItem> menu, BaseDataTableConfig datatable) {
        super(breadcrumbs, menu, datatable);
    }
}
