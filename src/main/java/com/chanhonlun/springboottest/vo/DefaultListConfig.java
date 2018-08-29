package com.chanhonlun.springboottest.vo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class DefaultListConfig extends BaseListConfig {

    private BaseDataTablesVO datatable;
}
