package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.form.BaseForm;
import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.response.page.BaseCreatePageConfig;
import com.chanhonlun.basecms.response.page.DefaultCreatePageConfig;
import com.chanhonlun.basecms.response.page.FormConfig;
import com.chanhonlun.basecms.response.vo.Field;
import com.chanhonlun.basecms.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import java.io.Serializable;
import java.util.Map;

public interface DefaultServiceHasCreatePageWithPojoDetail<
        Pojo extends BasePojo<PojoPK>,
        PojoPK extends Serializable,
        Form extends BaseForm>
        extends DefaultServiceHasCreatePage<Pojo, PojoPK, Form> {

    Map<String, Map<Language, Field>> getFieldDetailMap();

    default Map<String, Map<Language, Field>> getFieldDetailMapForCreate() {
        return getFieldDetailMap();
    }

    void updateFieldDetailMapValues(Map<String, Map<Language, Field>> fieldDetailMap, Form form);

    @Override
    default BaseCreatePageConfig getCreatePageConfig(Form form, FormError formError) {

        Map<String, Field> fieldMapClone = ReflectionUtil.cloneFieldMap(getFieldMapForCreate());
        Map<String, Map<Language, Field>> fieldDetailMapClone = ReflectionUtil.cloneFieldDetailMap(getFieldDetailMapForCreate());

        updateFieldMapValues(fieldMapClone, form);
        updateFieldDetailMapValues(fieldDetailMapClone, form);

        String pageTitle = StringUtils.capitalize(getSection().replaceAll("-", " "));

        return DefaultCreatePageConfig.builder()
                .pageTitle(pageTitle)
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(ReflectionUtil.getFields(fieldMapClone))
                .detailFields(ReflectionUtil.getDetailFields(fieldDetailMapClone))
                .formConfig(FormConfig.builder()
                        .id(getSection() + "-form")
                        .action(getContextPath() + "/" + getSection() + "/create")
                        .method(HttpMethod.POST.name())
                        .build())
                .formError(formError)
                .build();
    }

    @Override
    default BaseCreatePageConfig getCreatePageConfig() {

        String pageTitle = StringUtils.capitalize(getSection().replaceAll("-", " "));

        return DefaultCreatePageConfig.builder()
                .pageTitle(pageTitle)
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(ReflectionUtil.getFields(getFieldMapForCreate()))
                .detailFields(ReflectionUtil.getDetailFields(getFieldDetailMapForCreate()))
                .formConfig(FormConfig.builder()
                        .id(getSection() + "-form")
                        .action(getContextPath() + "/" + getSection() + "/create")
                        .method(HttpMethod.POST.name())
                        .build())
                .build();
    }
}
