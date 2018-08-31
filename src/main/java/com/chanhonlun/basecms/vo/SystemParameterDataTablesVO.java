package com.chanhonlun.basecms.vo;

import com.chanhonlun.basecms.model.DataTablesColumn;
import lombok.Builder;

import java.util.List;
import java.util.Map;

public class SystemParameterDataTablesVO extends BaseDataTablesVO {

    @Builder
    public SystemParameterDataTablesVO(String title, String dataTablesId, String ajaxUrl, List<DataTablesColumn> dataTablesColumns, Map<String, String> extraConfigs) {
        super(title, dataTablesId, ajaxUrl, dataTablesColumns, extraConfigs);
    }
}
