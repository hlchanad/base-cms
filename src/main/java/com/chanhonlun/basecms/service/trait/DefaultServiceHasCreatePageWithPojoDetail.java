package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.page.BaseCreatePageConfig;
import com.chanhonlun.basecms.response.page.DefaultCreatePageConfig;
import com.chanhonlun.basecms.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public interface DefaultServiceHasCreatePageWithPojoDetail extends DefaultServiceHasCreatePage {

    Map<String, Map<Language, Field>> getFieldDetailMap();

    @Override
    default BaseCreatePageConfig getCreatePageConfig() {

        String pageTitle = StringUtils.capitalize(getSection().replaceAll("-", " "));

        return DefaultCreatePageConfig.builder()
                .pageTitle(pageTitle)
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(ReflectionUtil.getFields(getFieldMap()))
                .detailFields(ReflectionUtil.getDetailFields(getFieldDetailMap()))
                .build();
    }
}
