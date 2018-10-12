package com.chanhonlun.basecms.response.page;

import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.response.vo.*;
import lombok.Builder;

import java.util.List;

public class DefaultDetailPageConfig extends BaseDetailPageConfig {

    @Builder
    public DefaultDetailPageConfig(String pageTitle, List<Breadcrumb> breadcrumbs, List<MenuItem> menu, List<Field> fields, List<DetailField> detailFields, FormConfig formConfig, FormError formError, List<DetailButton> detailButtons) {
        super(pageTitle, breadcrumbs, menu, fields, detailFields, formConfig, formError, detailButtons);
    }
}
