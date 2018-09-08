package com.chanhonlun.basecms.service.datatable.impl;

import com.chanhonlun.basecms.model.DataTableColumn;
import com.chanhonlun.basecms.model.component.BaseDataTableConfig;
import com.chanhonlun.basecms.model.component.DefaultDataTableConfig;
import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.repository.SystemParameterRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.vo.row.SystemParameterRowVO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SystemParameterDataTableServiceImpl extends BaseDataTableServiceImpl implements
        BaseDataTableService<SystemParameter, Long, SystemParameterRowVO, BaseDataTableInput, BaseDataTableConfig> {

    @Autowired
    private SystemParameterRepository systemParameterRepository;

    @Override
    protected String getSection() {
        return "system-parameter";
    }

    @Override
    public DataTablesRepository<SystemParameter, Long> getDataTablesRepository() {
        return systemParameterRepository;
    }

    @Override
    public SystemParameterRowVO getTableVOFromPOJO(SystemParameter systemParameter) {

        return SystemParameterRowVO.builder()
                .id(systemParameter.getId())
                .category(systemParameter.getCategory())
                .key(systemParameter.getKey())
                .value(systemParameter.getValue())
                .description(systemParameter.getDescription())
                .action(new Gson().toJson(actionButtonUtil.get(systemParameter.getId())))
                .build();
    }

    @Override
    public BaseDataTableConfig getDataTablesConfig(Map<String, String> extraConfigs) {

        List<DataTableColumn> dataTableColumns = Arrays.asList(
                DataTableColumn.builder().data("id").title("ID").build(),
                DataTableColumn.builder().data("category").title("Category").build(),
                DataTableColumn.builder().data("key").title("Key").build(),
                DataTableColumn.builder().data("value").title("Value").build(),
                DataTableColumn.builder().data("description").title("Description").build(),
                DataTableColumn.builder().data("action").title("Action").orderable(false).searchable(false).build()
        );

        return DefaultDataTableConfig.builder()
                .title("System Parameter")
                .dataTableId("system-parameter")
                .ajaxUrl(contextPath + "/system-parameter/data")
                .dataTableColumns(dataTableColumns)
                .extraConfigs(extraConfigs)
                .build();
    }
}
