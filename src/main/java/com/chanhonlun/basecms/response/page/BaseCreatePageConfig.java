package com.chanhonlun.basecms.response.page;

import com.chanhonlun.basecms.response.Breadcrumb;
import com.chanhonlun.basecms.response.DetailField;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.MenuItem;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

public class BaseCreatePageConfig extends BaseBlankPageConfig {

    public List<Field> fields;
    public List<DetailField> detailFields;

    @Builder
    public BaseCreatePageConfig(String pageTitle, List<Breadcrumb> breadcrumbs, List<MenuItem> menu, List<Field> fields, List<DetailField> detailFields) {
        super(pageTitle, breadcrumbs, menu);
        this.fields = fields;
        this.detailFields = detailFields == null ? new ArrayList<>() : detailFields;
    }
}
