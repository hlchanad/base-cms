package com.chanhonlun.basecms.model.component;

import com.chanhonlun.basecms.model.DataTableColumn;
import lombok.Builder;

import java.util.List;
import java.util.Map;

public class DefaultDataTableConfig extends BaseDataTableConfig {

    @Builder
    public DefaultDataTableConfig(String title, String dataTableId, String ajaxUrl, List<DataTableColumn> dataTableColumns, Map<String, String> extraConfigs) {
        super(title, dataTableId, ajaxUrl, dataTableColumns, extraConfigs);
    }
}

