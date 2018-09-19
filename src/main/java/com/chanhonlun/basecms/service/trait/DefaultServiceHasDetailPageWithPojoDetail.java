package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.pojo.BaseDetailPojo;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.repository.BaseDetailRepository;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.page.BaseCreatePageConfig;
import com.chanhonlun.basecms.response.page.DefaultCreatePageConfig;
import com.chanhonlun.basecms.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public interface DefaultServiceHasDetailPageWithPojoDetail<
        Pojo extends BasePojo<PojoPK>,
        PojoPK extends Serializable,
        PojoDetail extends BaseDetailPojo<PojoDetailPK, PojoPK>,
        PojoDetailPK extends Serializable>
        extends DefaultServiceHasDetailPage<Pojo, PojoPK> {

    String getSection();

    BaseDetailRepository<PojoDetail, PojoDetailPK, PojoPK> getDetailRepository();

    Map<String, Map<Language, Field>> getFieldDetailMap();

    default BaseCreatePageConfig getDetailPageConfig(Pojo pojo) {

        String pageTitle = StringUtils.capitalize(getSection().replaceAll("-", " "));

        Map<String, Field> fieldMap = ReflectionUtil.updateFieldMapWithValues(getFieldMap(), pojo);
        Map<String, Map<Language, Field>> fieldDetailMap = ReflectionUtil.updateFieldDetailMapWithValues(
                getFieldDetailMap(), pojo, getDetailRepository()::findByRefIdAndLang);

        return DefaultCreatePageConfig.builder()
                .pageTitle(pageTitle)
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(new ArrayList<>(fieldMap.values()))
                .detailFields(ReflectionUtil.getDetailFields(fieldDetailMap))
                .build();
    }

}
