package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.page.BaseCreatePageConfig;
import com.chanhonlun.basecms.response.page.DefaultCreatePageConfig;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public interface DefaultServiceHasDetailPage<
        Pojo extends BasePojo<PK>,
        PK extends Serializable> {

    Logger getLogger();

    String getSection();

    Map<String, Field> getFieldMap();

    BreadcrumbUtil getBreadcrumbUtil();

    SidebarMenuUtil getSidebarMenuUtil();

    BaseRepository<Pojo, PK> getRepository();

    default BaseCreatePageConfig getDetailPageConfig(PK id) {
        return getDetailPageConfig(getRepository().findByIdAndIsDeleteFalse(id));
    }

    default BaseCreatePageConfig getDetailPageConfig(Pojo pojo) {

        String pageTitle = StringUtils.capitalize(getSection().replaceAll("-", " "));

        Map<String, Field> fieldMap = ReflectionUtil.updateFieldMapWithValues(getFieldMap(), pojo);

        return DefaultCreatePageConfig.builder()
                .pageTitle(pageTitle)
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(new ArrayList<>(fieldMap.values()))
                .detailFields(Collections.emptyList())
                .build();
    }

}
