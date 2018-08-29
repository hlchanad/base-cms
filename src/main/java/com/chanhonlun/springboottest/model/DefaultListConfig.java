package com.chanhonlun.springboottest.model;

import com.chanhonlun.springboottest.vo.BaseDataTablesVO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class DefaultListConfig extends BaseListConfig {

    private BaseDataTablesVO datatable;
}
