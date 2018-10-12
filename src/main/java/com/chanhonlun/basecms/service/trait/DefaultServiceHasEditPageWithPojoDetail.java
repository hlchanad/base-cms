package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.form.BaseForm;
import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.pojo.BaseDetailPojo;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.repository.BaseDetailRepository;
import com.chanhonlun.basecms.response.vo.Field;
import com.chanhonlun.basecms.response.page.BaseEditPageConfig;
import com.chanhonlun.basecms.response.page.DefaultEditPageConfig;
import com.chanhonlun.basecms.response.page.FormConfig;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import java.io.Serializable;
import java.util.Map;

public interface DefaultServiceHasEditPageWithPojoDetail<
        Pojo extends BasePojo<PojoPK>,
        PojoPK extends Serializable,
        PojoDetail extends BaseDetailPojo<PojoDetailPK, PojoPK>,
        PojoDetailPK extends Serializable,
        Form extends BaseForm>
        extends DefaultServiceHasEditPage<Pojo, PojoPK, Form> {

    Map<String, Map<Language, Field>> getFieldDetailMap();

    default Map<String, Map<Language, Field>> getFieldDetailMapForEdit() {
        return getFieldDetailMap();
    }

    BaseDetailRepository<PojoDetail, PojoDetailPK, PojoPK> getDetailRepository();

    void updateFieldDetailMapValues(Map<String, Map<Language, Field>> fieldDetailMap, Form form);

    @Override
    default BaseEditPageConfig getEditPageConfig(Pojo pojo) {

        String pageTitle = StringUtils.capitalize(getSection().replaceAll("-", " "));

        Map<String, Field> fieldMap = ReflectionUtil.updateFieldMapWithValues(getFieldMap(), pojo);
        Map<String, Map<Language, Field>> fieldDetailMap = ReflectionUtil.updateFieldDetailMapWithValues(
                getFieldDetailMapForEdit(), pojo, getDetailRepository()::findByRefIdAndLang);

        return DefaultEditPageConfig.builder()
                .pageTitle(pageTitle)
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(ReflectionUtil.getFields(fieldMap))
                .detailFields(ReflectionUtil.getDetailFields(fieldDetailMap))
                .formConfig(FormConfig.builder()
                        .id(getSection() + "-form")
                        .action(getContextPath() + "/" + getSection() + "/" + pojo.getId() + "/edit")
                        .method(HttpMethod.PUT.name())
                        .build())
                .build();
    }

    @Override
    default BaseEditPageConfig getEditPageConfig(Pojo pojo, Form form, FormError formError) {

        Gson gson = new Gson();

        Map<String, Field> fieldMapClone = gson.fromJson(gson.toJson(getFieldMap()),
                new TypeToken<Map<String, Field>>() {}.getType());
        Map<String, Map<Language, Field>> fieldDetailMapClone = gson.fromJson(gson.toJson(getFieldDetailMapForEdit()),
                new TypeToken<Map<String, Map<Language, Field>>>() {}.getType());

        updateFieldMapValues(fieldMapClone, form);
        updateFieldDetailMapValues(fieldDetailMapClone, form);

        String pageTitle = StringUtils.capitalize(getSection().replaceAll("-", " "));

        return DefaultEditPageConfig.builder()
                .pageTitle(pageTitle)
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(ReflectionUtil.getFields(fieldMapClone))
                .detailFields(ReflectionUtil.getDetailFields(fieldDetailMapClone))
                .formConfig(FormConfig.builder()
                        .id(getSection() + "-form")
                         .action(getContextPath() + "/" + getSection() + "/" + pojo.getId() + "/edit")
                        .method(HttpMethod.PUT.name())
                        .build())
                .formError(formError)
                .build();
    }
}
