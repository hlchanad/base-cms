package com.chanhonlun.basecms.response.component;

import com.chanhonlun.basecms.response.vo.DataTableColumn;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public abstract class BaseDataTableConfig {

    private String title;
    private String dataTableId;
    private String ajaxUrl;
    private List<DataTableColumn> dataTableColumns;
    private Map<String, String> extraConfigs;

    public BaseDataTableConfig(String title, String dataTableId, String ajaxUrl, List<DataTableColumn> dataTableColumns, Map<String, String> extraConfigs) {
        this.title = title;
        this.dataTableId = dataTableId;
        this.ajaxUrl = ajaxUrl;
        this.dataTableColumns = dataTableColumns;
        this.extraConfigs = extraConfigs;
    }
}
