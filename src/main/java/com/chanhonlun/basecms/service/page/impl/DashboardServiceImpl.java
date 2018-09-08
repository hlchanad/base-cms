package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.model.page.BaseBlankPageConfig;
import com.chanhonlun.basecms.model.page.DefaultBlankPageConfig;
import com.chanhonlun.basecms.service.page.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl extends BaseServiceImpl implements DashboardService {

    @Override
    public BaseBlankPageConfig getPageConfig() {
        return DefaultBlankPageConfig.builder()
                .breadcrumbs(breadcrumbUtil.getBreadcrumbs())
                .menu(sidebarMenuUtil.getSidebarMenuList())
                .build();
    }
}
