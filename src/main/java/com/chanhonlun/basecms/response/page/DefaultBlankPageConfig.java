package com.chanhonlun.basecms.response.page;

import com.chanhonlun.basecms.response.Breadcrumb;
import com.chanhonlun.basecms.response.MenuItem;
import lombok.Builder;

import java.util.List;

public class DefaultBlankPageConfig extends BaseBlankPageConfig {

    @Builder
    public DefaultBlankPageConfig(List<Breadcrumb> breadcrumbs, List<MenuItem> menu) {
        super(breadcrumbs, menu);
    }
}
