package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.form.BaseForm;
import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.form.PostForm;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.page.BaseCreatePageConfig;
import com.chanhonlun.basecms.response.page.DefaultCreatePageConfig;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public interface DefaultServiceHasCreatePageWithPojoDetail<Form extends BaseForm>
        extends DefaultServiceHasCreatePage<Form> {

    Map<String, Map<Language, Field>> getFieldDetailMap();

    void updateFieldDetailMapValues(Map<String, Map<Language, Field>> fieldDetailMap, Form form);

    @Override
    default BaseCreatePageConfig getCreatePageConfig(Form form, FormError formError) {

        Gson gson = new Gson();

        Map<String, Field> fieldMapClone = gson.fromJson(gson.toJson(getFieldMap()), new TypeToken<Map<String, Field>>(){}.getType());
        Map<String, Map<Language, Field>> fieldDetailMapClone =
                gson.fromJson(gson.toJson(getFieldDetailMap()), new TypeToken<Map<String, Map<Language, Field>>>(){}.getType());

        updateFieldMapValues(fieldMapClone, form);
        updateFieldDetailMapValues(fieldDetailMapClone, form);

        String pageTitle = StringUtils.capitalize(getSection().replaceAll("-", " "));

        return DefaultCreatePageConfig.builder()
                .pageTitle(pageTitle)
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(ReflectionUtil.getFields(fieldMapClone))
                .detailFields(ReflectionUtil.getDetailFields(fieldDetailMapClone))
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
                .fields(ReflectionUtil.getFields(getFieldMap()))
                .detailFields(ReflectionUtil.getDetailFields(getFieldDetailMap()))
                .build();
    }
}
