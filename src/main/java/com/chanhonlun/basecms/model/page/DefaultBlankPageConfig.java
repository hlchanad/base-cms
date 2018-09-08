package com.chanhonlun.basecms.model.page;

import com.chanhonlun.basecms.model.Breadcrumb;
import com.chanhonlun.basecms.model.MenuItem;
import lombok.Builder;

import java.util.List;

public class DefaultBlankPageConfig extends BaseBlankPageConfig {

    @Builder
    public DefaultBlankPageConfig(List<Breadcrumb> breadcrumbs, List<MenuItem> menu) {
        super(breadcrumbs, menu);
    }
}
