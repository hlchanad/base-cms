package com.chanhonlun.basecms.service.datatable.impl;

import com.chanhonlun.basecms.pojo.Role;
import com.chanhonlun.basecms.repository.RoleRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.response.DataTableColumn;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.component.DefaultDataTableConfig;
import com.chanhonlun.basecms.response.vo.row.RoleRowVO;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.google.gson.Gson;
import com.mysema.codegen.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class RoleDataTableServiceImpl extends BaseDataTableServiceImpl implements
        BaseDataTableService<Role, Long, RoleRowVO, BaseDataTableInput, BaseDataTableConfig> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public DataTablesRepository<Role, Long> getDataTablesRepository() {
        return roleRepository;
    }

    @Override
    public RoleRowVO getTableVOFromPOJO(Role role) {
        return RoleRowVO.builder()
                .id(role.getId())
                .code(role.getCode())
                .title(role.getTitle())
                .description(role.getDescription())
                .action(new Gson().toJson(actionButtonUtil.get(role.getId())))
                .build();
    }

    @Override
    public BaseDataTableConfig getDataTableConfig(Map<String, String> extraConfigs) {
        List<DataTableColumn> dataTableColumns = Arrays.asList(
                DataTableColumn.builder().data("id").title("ID").build(),
                DataTableColumn.builder().data("code").title("Code").build(),
                DataTableColumn.builder().data("title").title("Title").build(),
                DataTableColumn.builder().data("description").title("Description").build(),
                DataTableColumn.builder().data("action").title("Action").orderable(false).searchable(false).build()
        );

        return DefaultDataTableConfig.builder()
                .title(StringUtils.capitalize(section.replace("-", " ")))
                .dataTableId(section)
                .ajaxUrl(contextPath + "/" + section + "/data")
                .dataTableColumns(dataTableColumns)
                .extraConfigs(extraConfigs)
                .build();
    }
}
