package com.chanhonlun.basecms.response.page;

import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.response.vo.*;

import java.util.List;

public abstract class BaseDetailPageConfig extends BaseBlankPageConfig {

    public List<Field> fields;
    public List<DetailField> detailFields;
    public FormConfig formConfig;
    public FormError formError;
    public List<DetailButton> detailButtons;

    public BaseDetailPageConfig(String pageTitle, List<Breadcrumb> breadcrumbs, List<MenuItem> menu, List<Field> fields, List<DetailField> detailFields, FormConfig formConfig, FormError formError, List<DetailButton> detailButtons) {
        super(pageTitle, breadcrumbs, menu);
        this.fields = fields;
        this.detailFields = detailFields;
        this.formConfig = formConfig;
        this.formError = formError;
        this.detailButtons = detailButtons;
    }
}
