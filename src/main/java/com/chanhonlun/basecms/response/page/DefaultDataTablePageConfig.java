package com.chanhonlun.basecms.response.page;

import com.chanhonlun.basecms.response.vo.Breadcrumb;
import com.chanhonlun.basecms.response.vo.MenuItem;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import lombok.Builder;

import java.util.List;

public class DefaultDataTablePageConfig extends BaseDataTablePageConfig {

    @Builder
    public DefaultDataTablePageConfig(String pageTitle, List<Breadcrumb> breadcrumbs, List<MenuItem> menu, BaseDataTableConfig datatable) {
        super(pageTitle, breadcrumbs, menu, datatable);
    }
}
