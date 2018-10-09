package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.page.DefaultDetailPageConfig;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public interface DefaultServiceHasDetailPage<
        Pojo extends BasePojo<PK>,
        PK extends Serializable> {

    String getSection();

    String getPageTitle();

    String getContextPath();

    Map<String, Field> getFieldMap();

    default Map<String, Field> getFieldMapForDetail() {
        return getFieldMap();
    }

    BreadcrumbUtil getBreadcrumbUtil();

    SidebarMenuUtil getSidebarMenuUtil();

    BaseRepository<Pojo, PK> getRepository();

    default DefaultDetailPageConfig getDetailPageConfig(PK id) {
        return getDetailPageConfig(getRepository().findByIdAndIsDeleteFalse(id));
    }

    default DefaultDetailPageConfig getDetailPageConfig(Pojo pojo) {

        Map<String, Field> fieldMap = ReflectionUtil.updateFieldMapWithValues(getFieldMapForDetail(), pojo);

        return DefaultDetailPageConfig.builder()
                .pageTitle(getPageTitle())
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(ReflectionUtil.getFields(fieldMap))
                .detailFields(Collections.emptyList())
                .listUrl(getContextPath() + "/" + getSection())
                .editUrl(getContextPath() + "/" + getSection() + "/" + pojo.getId() + "/edit")
                .deleteUrl(getContextPath() + "/" + getSection() + "/" + pojo.getId() + "/delete")
                .build();
    }

}
