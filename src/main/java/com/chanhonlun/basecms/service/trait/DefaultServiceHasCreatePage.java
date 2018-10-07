package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.form.BaseForm;
import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.page.BaseCreatePageConfig;
import com.chanhonlun.basecms.response.page.DefaultCreatePageConfig;
import com.chanhonlun.basecms.response.page.FormConfig;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.http.HttpMethod;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public interface DefaultServiceHasCreatePage<
        Pojo extends BasePojo<PojoPK>,
        PojoPK extends Serializable,
        Form extends BaseForm> {

    String getSection();

    String getPageTitle();

    String getContextPath();

    Map<String, Field> getFieldMap();

    default Map<String, Field> getFieldMapForCreate() {
        return getFieldMap();
    }

    BreadcrumbUtil getBreadcrumbUtil();

    SidebarMenuUtil getSidebarMenuUtil();

    Pojo create(Form form);

    default FormError ifCreateError(Form form) {
        return null;
    }

    void updateFieldMapValues(Map<String, Field> fieldMap, Form form);

    default BaseCreatePageConfig getCreatePageConfig(Form form, FormError formError) {

        Gson gson = new Gson();

        Map<String, Field> fieldMapClone = gson.fromJson(gson.toJson(getFieldMapForCreate()),
                new TypeToken<Map<String, Field>>() {}.getType());

        updateFieldMapValues(fieldMapClone, form);

        return DefaultCreatePageConfig.builder()
                .pageTitle(getPageTitle())
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(ReflectionUtil.getFields(fieldMapClone))
                .detailFields(Collections.emptyList())
                .formConfig(FormConfig.builder()
                        .id(getSection() + "-form")
                        .action(getContextPath() + "/" + getSection() + "/create")
                        .method(HttpMethod.POST.name())
                        .build())
                .formError(formError)
                .build();
    }

    default BaseCreatePageConfig getCreatePageConfig() {

        return DefaultCreatePageConfig.builder()
                .pageTitle(getPageTitle())
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(ReflectionUtil.getFields(getFieldMapForCreate()))
                .detailFields(Collections.emptyList())
                .formConfig(FormConfig.builder()
                        .id(getSection() + "-form")
                        .action(getContextPath() + "/" + getSection() + "/create")
                        .method(HttpMethod.POST.name())
                        .build())
                .build();
    }

}
