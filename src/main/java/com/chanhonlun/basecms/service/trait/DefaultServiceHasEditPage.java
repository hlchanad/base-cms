package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.form.BaseForm;
import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.response.page.BaseEditPageConfig;
import com.chanhonlun.basecms.response.page.DefaultEditPageConfig;
import com.chanhonlun.basecms.response.page.FormConfig;
import com.chanhonlun.basecms.response.vo.Field;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import org.springframework.http.HttpMethod;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public interface DefaultServiceHasEditPage<
        Pojo extends BasePojo<PojoPK>,
        PojoPK extends Serializable,
        Form extends BaseForm> {

    String getSection();

    String getPageTitle();

    String getContextPath();

    Map<String, Field> getFieldMap();

    default Map<String, Field> getFieldMapForEdit(Pojo pojo) {
        return getFieldMap();
    }

    BreadcrumbUtil getBreadcrumbUtil();

    SidebarMenuUtil getSidebarMenuUtil();

    Pojo edit(Pojo pojo, Form form);

    void updateFieldMapValues(Map<String, Field> fieldMap, Form form);

    default void updateFieldMapValuesForEdit(Map<String, Field> fieldMap, Pojo pojo) {
        ReflectionUtil.updateFieldMapWithValues(fieldMap, pojo);
    }

    default FormError ifEditError(Pojo pojo, Form form) {
        return null;
    }

    default BaseEditPageConfig getEditPageConfig(Pojo pojo) {

        Map<String, Field> fieldMapClone = ReflectionUtil.cloneFieldMap(getFieldMapForEdit(pojo));
        updateFieldMapValuesForEdit(fieldMapClone, pojo);

        return DefaultEditPageConfig.builder()
                .pageTitle(getPageTitle())
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(ReflectionUtil.getFields(fieldMapClone))
                .detailFields(Collections.emptyList())
                .formConfig(FormConfig.builder()
                        .id(getSection() + "-form")
                        .action(getContextPath() + "/" + getSection() + "/" + pojo.getId() + "/edit")
                        .method(HttpMethod.PUT.name())
                        .build())
                .build();
    }

    default BaseEditPageConfig getEditPageConfig(Pojo pojo, Form form, FormError formError) {

        Map<String, Field> fieldMapClone = ReflectionUtil.cloneFieldMap(getFieldMapForEdit(pojo));

        updateFieldMapValues(fieldMapClone, form);

        return DefaultEditPageConfig.builder()
                .pageTitle(getPageTitle())
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(ReflectionUtil.getFields(fieldMapClone))
                .detailFields(Collections.emptyList())
                .formConfig(FormConfig.builder()
                        .id(getSection() + "-form")
                        .action(getContextPath() + "/" + getSection() + "/" + pojo.getId() + "/edit")
                        .method(HttpMethod.PUT.name())
                        .build())
                .formError(formError)
                .build();
    }

}
