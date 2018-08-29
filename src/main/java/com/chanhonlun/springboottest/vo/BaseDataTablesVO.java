package com.chanhonlun.springboottest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class BaseDataTablesVO {

    private String title;
    private String dataTablesId;
    private String ajaxUrl;
    private List<DataTablesColumn> dataTablesColumns;
    private Map<String, String> extraConfigs;
}
