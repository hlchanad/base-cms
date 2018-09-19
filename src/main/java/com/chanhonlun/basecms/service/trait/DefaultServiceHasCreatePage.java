package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.page.BaseCreatePageConfig;
import com.chanhonlun.basecms.response.page.DefaultCreatePageConfig;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public interface DefaultServiceHasCreatePage {

    String getSection();

    Map<String, Field> getFieldMap();

    BreadcrumbUtil getBreadcrumbUtil();

    SidebarMenuUtil getSidebarMenuUtil();

    default BaseCreatePageConfig getCreatePageConfig() {

        String pageTitle = StringUtils.capitalize(getSection().replaceAll("-", " "));

        return DefaultCreatePageConfig.builder()
                .pageTitle(pageTitle)
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(ReflectionUtil.getFields(getFieldMap()))
                .detailFields(Collections.emptyList())
                .build();
    }

}
