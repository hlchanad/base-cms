package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.pojo.BaseDetailPojo;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.repository.BaseDetailRepository;
import com.chanhonlun.basecms.response.page.DefaultDetailPageConfig;
import com.chanhonlun.basecms.response.vo.Field;
import com.chanhonlun.basecms.util.ReflectionUtil;

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

    default Map<String, Map<Language, Field>> getFieldDetailMapForDetail(Pojo pojo) {
        return getFieldDetailMap();
    }

    default void updateFieldDetailMapValuesForDetail(Map<String, Map<Language, Field>> fieldDetailMap, Pojo pojo) {
        ReflectionUtil.updateFieldDetailMapWithValues(fieldDetailMap, pojo, getDetailRepository()::findByRefIdAndLang);
    }

    @Override
    default DefaultDetailPageConfig getDetailPageConfig(Pojo pojo) {

        Map<String, Field> fieldMapClone = ReflectionUtil.cloneFieldMap(getFieldMapForDetail(pojo));
        Map<String, Map<Language, Field>> fieldDetailMapClone = ReflectionUtil.cloneFieldDetailMap(getFieldDetailMapForDetail(pojo));

        updateFieldMapValuesForDetail(fieldMapClone, pojo);
        updateFieldDetailMapValuesForDetail(fieldDetailMapClone, pojo);

        return DefaultDetailPageConfig.builder()
                .pageTitle(getPageTitle())
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .fields(ReflectionUtil.getFields(fieldMapClone))
                .detailFields(ReflectionUtil.getDetailFields(fieldDetailMapClone))
                .detailButtons(getDetailButtons(pojo))
                .build();
    }

}
