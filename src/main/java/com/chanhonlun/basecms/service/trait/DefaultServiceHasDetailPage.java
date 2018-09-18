package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.annotation.IgnoreAutoReflection;
import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.pojo.Post;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.page.BaseCreatePageConfig;
import com.chanhonlun.basecms.response.page.DefaultCreatePageConfig;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import com.google.common.base.CaseFormat;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
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

    default Map<String, Field> updateFieldMapWithValues(Map<String, Field> fieldMap, Pojo pojo) {

        Gson gson = new Gson();

        Map<String, Field> fieldMapClone = gson.fromJson(gson.toJson(fieldMap), new TypeToken<Map<String, Field>>(){}.getType());

        ReflectionUtil.getPojoFields(pojo.getClass())
                .stream()
                .filter(property -> property.getAnnotation(IgnoreAutoReflection.class) == null)
                .forEach(property -> {
                    try {
                        Method getter = pojo.getClass().getMethod("get" + StringUtils.capitalize(property.getName()));
                        String value = getter.invoke(pojo).toString().replaceAll("\\n", "<br/>");
                        fieldMapClone.get(property.getName()).setValue(value);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        getLogger().error("cannot call getter method for field: {}, e: {}", property.getName(), e);
                    }
                });

        return fieldMapClone;
    }

    default BaseCreatePageConfig getDetailPageConfig(PK id) {
        return getDetailPageConfig(getRepository().findByIdAndIsDeleteFalse(id));
    }

    default BaseCreatePageConfig getDetailPageConfig(Pojo pojo) {

        String pageTitle = StringUtils.capitalize(getSection().replaceAll("-", " "));

        Map<String, Field> fieldMap = updateFieldMapWithValues(getFieldMap(), pojo);

        return DefaultCreatePageConfig.builder()
                .pageTitle(pageTitle)
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(new ArrayList<>(fieldMap.values()))
                .detailFields(Collections.emptyList())
                .build();
    }

}
