package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.response.page.BaseBlankPageConfig;
import com.chanhonlun.basecms.response.page.DefaultBlankPageConfig;
import com.chanhonlun.basecms.service.page.DashboardPageService;
import org.springframework.stereotype.Service;

@Service
public class DashboardPageServiceImpl extends BasePageServiceImpl implements DashboardPageService {

    @Override
    public BaseBlankPageConfig getPageConfig() {
        return DefaultBlankPageConfig.builder()
                .pageTitle("Dashboard")
                .breadcrumbs(breadcrumbUtil.getBreadcrumbs())
                .menu(sidebarMenuUtil.getSidebarMenuList())
                .build();
    }
}
