package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.constant.DetailButtonType;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.response.vo.DetailButton;
import com.chanhonlun.basecms.response.vo.Field;
import com.chanhonlun.basecms.response.page.DefaultDetailPageConfig;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;

import java.io.Serializable;
import java.util.Arrays;
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

    default Map<String, Field> getFieldMapForDetail() {
        return getFieldMap();
    }

    BreadcrumbUtil getBreadcrumbUtil();

    SidebarMenuUtil getSidebarMenuUtil();

    BaseRepository<Pojo, PK> getRepository();

    default List<DetailButton> getDetailButtons(Pojo pojo) {
        return Arrays.asList(
                DetailButton.builder()
                        .type(DetailButtonType.REDIRECT)
                        .href(getContextPath() + "/" + getSection())
                        .faIcon("fa-list-ul")
                        .bootstrapColor("complete")
                        .build(),
                DetailButton.builder()
                        .type(DetailButtonType.REDIRECT)
                        .href(getContextPath() + "/" + getSection() + "/" + pojo.getId() + "/edit")
                        .faIcon("fa-pencil")
                        .bootstrapColor("primary")
                        .build(),
                DetailButton.builder()
                        .type(DetailButtonType.DELETE)
                        .href(getContextPath() + "/" + getSection() + "/" + pojo.getId() + "/delete")
                        .redirectUrl(getContextPath() + "/" + getSection())
                        .faIcon("fa-trash-o")
                        .bootstrapColor("danger")
                        .build()
        );
    }

    default DefaultDetailPageConfig getDetailPageConfig(PK id) {
        return getDetailPageConfig(getRepository().findByIdAndIsDeleteFalse(id));
    }

    default DefaultDetailPageConfig getDetailPageConfig(Pojo pojo) {

        Map<String, Field> fieldMapClone = ReflectionUtil.cloneFieldMap(getFieldMapForDetail());
        ReflectionUtil.updateFieldMapWithValues(fieldMapClone, pojo);

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
