package com.chanhonlun.basecms.vo;

import com.chanhonlun.basecms.model.DataTablesColumn;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

public class CmsUserDataTablesVO extends BaseDataTablesVO {

    @Builder
    public CmsUserDataTablesVO(String title, String dataTablesId, String ajaxUrl, List<DataTablesColumn> dataTablesColumns, Map<String, String> extraConfigs) {
        super(title, dataTablesId, ajaxUrl, dataTablesColumns, extraConfigs);
    }
}
