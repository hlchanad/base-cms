package com.chanhonlun.basecms.service.page;

import com.chanhonlun.basecms.response.page.BaseBlankPageConfig;

public interface PhotoGalleryPageService extends BasePageService {

    BaseBlankPageConfig getListConfig();

    BaseBlankPageConfig getCreateConfig();
}
