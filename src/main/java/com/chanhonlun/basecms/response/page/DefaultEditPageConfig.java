package com.chanhonlun.basecms.response.page;

import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.response.Breadcrumb;
import com.chanhonlun.basecms.response.DetailField;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.MenuItem;
import lombok.Builder;

import java.util.List;

public class DefaultEditPageConfig extends BaseEditPageConfig {

    @Builder
    public DefaultEditPageConfig(String pageTitle, List<Breadcrumb> breadcrumbs, List<MenuItem> menu, List<Field> fields, List<DetailField> detailFields, FormConfig formConfig, FormError formError) {
        super(pageTitle, breadcrumbs, menu, fields, detailFields, formConfig, formError);
    }
}