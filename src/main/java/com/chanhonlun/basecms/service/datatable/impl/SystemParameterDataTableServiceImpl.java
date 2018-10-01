package com.chanhonlun.basecms.service.datatable.impl;

import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.repository.SystemParameterRepository;
import com.chanhonlun.basecms.response.DataTableColumn;
import com.chanhonlun.basecms.response.vo.row.SystemParameterRowVO;
import com.chanhonlun.basecms.service.datatable.DefaultDataTableService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SystemParameterDataTableServiceImpl extends BaseDataTableServiceImpl implements
        DefaultDataTableService<SystemParameter, Long, SystemParameterRowVO> {

    @Autowired
    private SystemParameterRepository systemParameterRepository;

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
    public List<DataTableColumn> getDataTableColumns() {
        return Arrays.asList(
                DataTableColumn.builder().data("id").title("ID").build(),
                DataTableColumn.builder().data("category").title("Category").build(),
                DataTableColumn.builder().data("key").title("Key").build(),
                DataTableColumn.builder().data("value").title("Value").build(),
                DataTableColumn.builder().data("description").title("Description").build(),
                DataTableColumn.builder().data("action").title("Action").orderable(false).searchable(false).build()
        );
    }
}
