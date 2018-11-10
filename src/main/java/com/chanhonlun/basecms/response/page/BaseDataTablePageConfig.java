package com.chanhonlun.basecms.response.page;

import com.chanhonlun.basecms.response.vo.Breadcrumb;
import com.chanhonlun.basecms.response.vo.MenuItem;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseDataTablePageConfig extends BaseBlankPageConfig {

    private BaseDataTableConfig datatable;

    public BaseDataTablePageConfig(String pageTitle, List<Breadcrumb> breadcrumbs, List<MenuItem> menu, BaseDataTableConfig datatable) {
        super(pageTitle, breadcrumbs, menu);
        this.datatable = datatable;
    }
}
