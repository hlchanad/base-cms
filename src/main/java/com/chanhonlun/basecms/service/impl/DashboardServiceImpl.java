package com.chanhonlun.basecms.service.impl;

import com.chanhonlun.basecms.model.BaseConfig;
import com.chanhonlun.basecms.service.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl extends BaseServiceImpl implements DashboardService {

    @Override
    public BaseConfig getPageConfig() {
        return BaseConfig.builder()
                .breadcrumbs(breadcrumbUtil.getBreadcrumbs())
                .menu(sidebarMenuUtil.getSidebarMenuList())
                .build();
    }
}
