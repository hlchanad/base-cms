package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.response.page.DefaultDetailPageConfig;
import com.chanhonlun.basecms.response.vo.DetailButton;
import com.chanhonlun.basecms.response.vo.Field;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.DetailActionButtonUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface DefaultServiceHasDetailPage<
        Pojo extends BasePojo<PK>,
        PK extends Serializable> {

    String getSection();

    String getPageTitle();

    String getContextPath();

    Map<String, Field> getFieldMap();

    default Map<String, Field> getFieldMapForDetail(Pojo pojo) {
        return getFieldMap();
    }

    BreadcrumbUtil getBreadcrumbUtil();

    SidebarMenuUtil getSidebarMenuUtil();

    DetailActionButtonUtil getDetailActionButtonUtil();

    BaseRepository<Pojo, PK> getRepository();

    default List<DetailButton> getDetailButtons(Pojo pojo) {
        return getDetailActionButtonUtil().get(pojo.getId());
    }

    default DefaultDetailPageConfig getDetailPageConfig(PK id) {
        return getDetailPageConfig(getRepository().findByIdAndIsDeletedFalse(id));
    }

    default void updateFieldMapValuesForDetail(Map<String, Field> fieldMap, Pojo pojo) {
        ReflectionUtil.updateFieldMapWithValues(fieldMap, pojo);
    }

    default DefaultDetailPageConfig getDetailPageConfig(Pojo pojo) {

        Map<String, Field> fieldMapClone = ReflectionUtil.cloneFieldMap(getFieldMapForDetail(pojo));
        updateFieldMapValuesForDetail(fieldMapClone, pojo);

        return DefaultDetailPageConfig.builder()
                .pageTitle(getPageTitle())
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(ReflectionUtil.getFields(fieldMapClone))
                .detailFields(Collections.emptyList())
                .detailButtons(getDetailButtons(pojo))
                .build();
    }

}
