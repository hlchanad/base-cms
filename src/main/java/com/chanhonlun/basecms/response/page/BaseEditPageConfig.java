package com.chanhonlun.basecms.response.page;

import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.response.Breadcrumb;
import com.chanhonlun.basecms.response.DetailField;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.MenuItem;

import java.util.List;


public abstract class BaseEditPageConfig extends BaseBlankPageConfig {

    public List<Field>       fields;
    public List<DetailField> detailFields;
    public FormConfig        formConfig;
    public FormError         formError;

    public BaseEditPageConfig(String pageTitle, List<Breadcrumb> breadcrumbs, List<MenuItem> menu, List<Field> fields, List<DetailField> detailFields, FormConfig formConfig, FormError formError) {
        super(pageTitle, breadcrumbs, menu);
        this.fields = fields;
        this.detailFields = detailFields;
        this.formConfig = formConfig;
        this.formError = formError;
    }
}
