package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.response.page.BaseBlankPageConfig;
import com.chanhonlun.basecms.response.page.DefaultBlankPageConfig;
import com.chanhonlun.basecms.service.page.PhotoGalleryPageService;
import org.springframework.stereotype.Service;

@Service
public class PhotoGalleryPageServiceImpl extends BasePageServiceImpl implements PhotoGalleryPageService {

    @Override
    public BaseBlankPageConfig getListConfig() {
        return DefaultBlankPageConfig.builder()
                .pageTitle("Photo Gallery")
                .breadcrumbs(breadcrumbUtil.getBreadcrumbs())
                .menu(sidebarMenuUtil.getSidebarMenuList())
                .build();
    }

    @Override
    public BaseBlankPageConfig getCreateConfig() {
        return DefaultBlankPageConfig.builder()
                .pageTitle("Photo Gallery")
                .breadcrumbs(breadcrumbUtil.getBreadcrumbs())
                .menu(sidebarMenuUtil.getSidebarMenuList())
                .build();
    }
}

