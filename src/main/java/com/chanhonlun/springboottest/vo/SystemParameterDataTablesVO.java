package com.chanhonlun.springboottest.vo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

public class SystemParameterDataTablesVO extends BaseDataTablesVO {

    @Builder
    public SystemParameterDataTablesVO(String title, String dataTablesId, String ajaxUrl, List<DataTablesColumn> dataTablesColumns, Map<String, String> extraConfigs) {
        super(title, dataTablesId, ajaxUrl, dataTablesColumns, extraConfigs);
    }
}
