package com.chanhonlun.basecms.response.page;

import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.response.vo.Breadcrumb;
import com.chanhonlun.basecms.response.vo.DetailField;
import com.chanhonlun.basecms.response.vo.Field;
import com.chanhonlun.basecms.response.vo.MenuItem;
import lombok.Builder;

import java.util.List;

public class DefaultCreatePageConfig extends BaseCreatePageConfig {

    @Builder
    public DefaultCreatePageConfig(String pageTitle, List<Breadcrumb> breadcrumbs, List<MenuItem> menu, List<Field> fields, List<DetailField> detailFields, FormConfig formConfig, FormError formError) {
        super(pageTitle, breadcrumbs, menu, fields, detailFields, formConfig, formError);
    }
}
