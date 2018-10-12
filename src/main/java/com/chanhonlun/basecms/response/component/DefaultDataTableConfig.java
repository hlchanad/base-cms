package com.chanhonlun.basecms.response.component;

import com.chanhonlun.basecms.response.vo.DataTableColumn;
import lombok.Builder;

import java.util.List;
import java.util.Map;

public class DefaultDataTableConfig extends BaseDataTableConfig {

    @Builder
    public DefaultDataTableConfig(String title, String dataTableId, String ajaxUrl, List<DataTableColumn> dataTableColumns, Map<String, String> extraConfigs) {
        super(title, dataTableId, ajaxUrl, dataTableColumns, extraConfigs);
    }
}

