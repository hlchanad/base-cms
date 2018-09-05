package com.chanhonlun.basecms.service;

import com.chanhonlun.basecms.model.BaseListConfig;
import com.chanhonlun.basecms.model.DefaultListConfig;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import com.chanhonlun.basecms.vo.BaseDataTablesVO;
import com.chanhonlun.basecms.vo.BaseTableVO;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.io.Serializable;
import java.util.HashMap;

public interface DefaultPageHasDataTable<
        Pojo,
        PK extends Serializable,
        PojoVO extends BaseTableVO,
        Req extends DataTablesInput,
        RspVO extends BaseDataTablesVO> {

    DataTablesServiceTrait<Pojo, PK, PojoVO, Req, RspVO> getDataTablesService();

    BreadcrumbUtil getBreadcrumbUtil();

    SidebarMenuUtil getSidebarMenuUtil();

    default DataTablesOutput<PojoVO> dataTableAPI(Req input) {
        return getDataTablesService().getDataTablesData(input);
    }

    default BaseListConfig getListConfig() {
        return DefaultListConfig.builder()
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .datatable(getDataTablesService().getDataTablesConfig(new HashMap<>()))
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .build();
    }
}
