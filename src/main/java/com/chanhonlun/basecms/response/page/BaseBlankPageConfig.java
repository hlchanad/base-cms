package com.chanhonlun.basecms.response.page;

import com.chanhonlun.basecms.response.vo.Breadcrumb;
import com.chanhonlun.basecms.response.vo.MenuItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseBlankPageConfig extends BaseConfig {

    private List<Breadcrumb> breadcrumbs;

    private List<MenuItem> menu;

    public BaseBlankPageConfig(String pageTitle, List<Breadcrumb> breadcrumbs, List<MenuItem> menu) {
        super(pageTitle);
        this.breadcrumbs = breadcrumbs;
        this.menu = menu;
    }
}
