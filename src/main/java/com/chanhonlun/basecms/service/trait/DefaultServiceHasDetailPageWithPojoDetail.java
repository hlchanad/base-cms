package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.annotation.IgnoreAutoReflection;
import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.pojo.BaseDetailPojo;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.repository.BaseDetailRepository;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.page.BaseCreatePageConfig;
import com.chanhonlun.basecms.response.page.DefaultCreatePageConfig;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.google.common.base.CaseFormat;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

public interface DefaultServiceHasDetailPageWithPojoDetail<
        Pojo extends BasePojo<PojoPK>,
        PojoPK extends Serializable,
        PojoDetail extends BaseDetailPojo<PojoDetailPK, PojoPK>,
        PojoDetailPK extends Serializable>
        extends DefaultServiceHasDetailPage<Pojo, PojoPK> {

    BaseDetailRepository<PojoDetail, PojoDetailPK, PojoPK> getDetailRepository();

    Map<String, Map<Language, Field>> getFieldDetailMap();

    default Map<String, Map<Language, Field>> updateFieldDetailMapWithValues(Map<String, Map<Language, Field>> fieldDetailMap, Pojo pojo) {

        Gson gson = new Gson();

        Map<String, Map<Language, Field>> fieldDetailMapClone =
                gson.fromJson(gson.toJson(fieldDetailMap), new TypeToken<Map<String, Map<Language, Field>>>(){}.getType());

        Stream.of(Language.values())
                .map(language -> getDetailRepository().findByRefIdAndLang(pojo.getId(), language))
                .forEach(postDetail -> ReflectionUtil.getPojoFields(postDetail.getClass())
                        .stream()
                        .filter(property -> property.getAnnotation(IgnoreAutoReflection.class) == null)
                        .forEach(property -> {
                            try {
                                Method getter = postDetail.getClass().getMethod("get" + StringUtils.capitalize(property.getName()));
                                String value = getter.invoke(postDetail).toString().replaceAll("\\n", "<br/>");
                                fieldDetailMapClone.get(property.getName()).get(postDetail.getLang()).setValue(value);
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                getLogger().error("cannot call getter method for field: {}, e: {}", property.getName(), e);
                            }
                        }));

        return fieldDetailMapClone;
    }

    default BaseCreatePageConfig getDetailPageConfig(Pojo pojo) {

        String pageTitle = StringUtils.capitalize(CaseFormat.UPPER_CAMEL.to(
                CaseFormat.LOWER_UNDERSCORE, pojo.getClass().getSimpleName()).replaceAll("_", " "));

        Map<String, Field> fieldMap = updateFieldMapWithValues(getFieldMap(), pojo);
        Map<String, Map<Language, Field>> fieldDetailMap = updateFieldDetailMapWithValues(getFieldDetailMap(), pojo);

        return DefaultCreatePageConfig.builder()
                .pageTitle(pageTitle)
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(new ArrayList<>(fieldMap.values()))
                .detailFields(ReflectionUtil.getDetailFields(fieldDetailMap))
                .build();
    }

}
