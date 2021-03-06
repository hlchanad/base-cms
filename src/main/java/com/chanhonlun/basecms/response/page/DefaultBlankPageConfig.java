package com.chanhonlun.basecms.response.page;

import com.chanhonlun.basecms.response.vo.Breadcrumb;
import com.chanhonlun.basecms.response.vo.MenuItem;
import lombok.Builder;

import java.util.List;

public class DefaultBlankPageConfig extends BaseBlankPageConfig {

    @Builder
    public DefaultBlankPageConfig(String pageTitle, List<Breadcrumb> breadcrumbs, List<MenuItem> menu) {
        super(pageTitle, breadcrumbs, menu);
    }
}
