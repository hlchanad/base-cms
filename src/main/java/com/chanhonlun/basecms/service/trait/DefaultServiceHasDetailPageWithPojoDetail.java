package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.pojo.BaseDetailPojo;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.repository.BaseDetailRepository;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.page.DefaultDetailPageConfig;
import com.chanhonlun.basecms.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
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

    default Map<String, Map<Language, Field>> getFieldDetailMapForDetail() {
        return getFieldDetailMap();
    }

    @Override
    default DefaultDetailPageConfig getDetailPageConfig(Pojo pojo) {

        String pageTitle = StringUtils.capitalize(getSection().replaceAll("-", " "));

        Map<String, Field> fieldMap = ReflectionUtil.updateFieldMapWithValues(getFieldMap(), pojo);
        Map<String, Map<Language, Field>> fieldDetailMap = ReflectionUtil.updateFieldDetailMapWithValues(
                getFieldDetailMapForDetail(), pojo, getDetailRepository()::findByRefIdAndLang);

        return DefaultDetailPageConfig.builder()
                .pageTitle(pageTitle)
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(ReflectionUtil.getFields(fieldMap))
                .detailFields(ReflectionUtil.getDetailFields(fieldDetailMap))
                .listUrl(getContextPath() + "/" + getSection())
                .editUrl(getContextPath() + "/" + getSection() + "/" + pojo.getId() + "/edit")
                .deleteUrl(getContextPath() + "/" + getSection() + "/" + pojo.getId() + "/delete")
                .build();
    }

}
