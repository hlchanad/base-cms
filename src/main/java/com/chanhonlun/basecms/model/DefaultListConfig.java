package com.chanhonlun.basecms.model;

import com.chanhonlun.basecms.vo.BaseDataTablesVO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class DefaultListConfig extends BaseListConfig {

    private List<Breadcrumb> breadcrumbs;
    private BaseDataTablesVO datatable;
    private List<MenuItem> menu;
}