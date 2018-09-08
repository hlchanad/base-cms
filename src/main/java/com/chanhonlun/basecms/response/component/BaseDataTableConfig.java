package com.chanhonlun.basecms.response.component;

import com.chanhonlun.basecms.response.DataTableColumn;

import java.util.List;
import java.util.Map;

public abstract class BaseDataTableConfig {

    public String title;
    public String dataTableId;
    public String ajaxUrl;
    public List<DataTableColumn> dataTableColumns;
    public Map<String, String> extraConfigs;

    public BaseDataTableConfig(String title, String dataTableId, String ajaxUrl, List<DataTableColumn> dataTableColumns, Map<String, String> extraConfigs) {
        this.title = title;
        this.dataTableId = dataTableId;
        this.ajaxUrl = ajaxUrl;
        this.dataTableColumns = dataTableColumns;
        this.extraConfigs = extraConfigs;
    }
}
