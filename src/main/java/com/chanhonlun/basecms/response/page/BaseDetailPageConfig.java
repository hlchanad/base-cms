package com.chanhonlun.basecms.response.page;

import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.response.vo.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseDetailPageConfig extends BaseBlankPageConfig {

    private List<Field> fields;
    private List<DetailField> detailFields;
    private FormConfig formConfig;
    private FormError formError;
    private List<DetailButton> detailButtons;

    public BaseDetailPageConfig(String pageTitle, List<Breadcrumb> breadcrumbs, List<MenuItem> menu, List<Field> fields, List<DetailField> detailFields, FormConfig formConfig, FormError formError, List<DetailButton> detailButtons) {
        super(pageTitle, breadcrumbs, menu);
        this.fields = fields;
        this.detailFields = detailFields;
        this.formConfig = formConfig;
        this.formError = formError;
        this.detailButtons = detailButtons;
    }
}
