package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.page.BaseDataTablePageConfig;
import com.chanhonlun.basecms.response.page.DefaultDataTablePageConfig;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import com.chanhonlun.basecms.response.vo.row.BaseRowVO;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.io.Serializable;
import java.util.HashMap;

public interface DefaultServiceHasDataTable<
        Pojo,
        PK extends Serializable,
        PojoVO extends BaseRowVO> {

    BaseDataTableService<Pojo, PK, PojoVO, BaseDataTableInput, BaseDataTableConfig> getDataTablesService();

    BreadcrumbUtil getBreadcrumbUtil();

    SidebarMenuUtil getSidebarMenuUtil();

    default DataTablesOutput<PojoVO> dataTableAPI(BaseDataTableInput input) {
        return getDataTablesService().getDataTablesData(input);
    }

    default BaseDataTablePageConfig getListConfig() {
        return DefaultDataTablePageConfig.builder()
                .breadcrumbs(getBreadcrumbUtil().getBreadcrumbs())
                .datatable(getDataTablesService().getDataTablesConfig(new HashMap<>()))
                .menu(getSidebarMenuUtil().getSidebarMenuList())
                .build();
    }
}
