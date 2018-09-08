package com.chanhonlun.basecms.service.datatable.impl;

import com.chanhonlun.basecms.response.DataTableColumn;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.component.DefaultDataTableConfig;
import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.repository.CmsUserRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.response.vo.row.CmsUserRowVO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CmsUserDataTableServiceImpl extends BaseDataTableServiceImpl implements
        BaseDataTableService<CmsUser, Long, CmsUserRowVO, BaseDataTableInput, BaseDataTableConfig> {

    @Autowired
    private CmsUserRepository cmsUserRepository;

    @Override
    public DataTablesRepository<CmsUser, Long> getDataTablesRepository() {
        return cmsUserRepository;
    }

    @Override
    protected String getSection() {
        return "cms-user";
    }

    @Override
    public CmsUserRowVO getTableVOFromPOJO(CmsUser cmsUser) {

        return CmsUserRowVO.builder()
                .id(cmsUser.getId())
                .username(cmsUser.getUsername())
                .email(cmsUser.getEmail())
                .action(new Gson().toJson(actionButtonUtil.get(cmsUser.getId())))
                .build();
    }

    @Override
    public BaseDataTableConfig getDataTableConfig(Map<String, String> extraConfigs) {

        List<DataTableColumn> dataTableColumns = Arrays.asList(
                DataTableColumn.builder().data("id").title("ID").build(),
                DataTableColumn.builder().data("username").title("Username").build(),
                DataTableColumn.builder().data("email").title("Email").build(),
                DataTableColumn.builder().data("action").title("Action").orderable(false).searchable(false).build()
        );

        return DefaultDataTableConfig.builder()
                .title("CMS User")
                .dataTableId("cms-user")
                .ajaxUrl(contextPath + "/cms-user/data")
                .dataTableColumns(dataTableColumns)
                .extraConfigs(extraConfigs)
                .build();
    }
}
