package com.chanhonlun.basecms.service.impl;

import com.chanhonlun.basecms.model.DataTablesColumn;
import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.repository.SystemParameterRepository;
import com.chanhonlun.basecms.req.datatables.SystemParameterListDataTablesInput;
import com.chanhonlun.basecms.service.DataTablesServiceTrait;
import com.chanhonlun.basecms.vo.SystemParameterDataTablesVO;
import com.chanhonlun.basecms.vo.SystemParameterTableVO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SystemParameterDataTablesService extends BaseDataTablesService implements
        DataTablesServiceTrait<SystemParameter, Long, SystemParameterTableVO, SystemParameterListDataTablesInput, SystemParameterDataTablesVO> {

    @Autowired
    private SystemParameterRepository systemParameterRepository;

    @PostConstruct
    public void init() {
        actionButtonUtil.init("system-parameter");
    }

    @Override
    public DataTablesRepository<SystemParameter, Long> getDataTablesRepository() {
        return systemParameterRepository;
    }

    @Override
    public SystemParameterTableVO getTableVOFromPOJO(SystemParameter systemParameter) {

        return SystemParameterTableVO.builder()
                .id(systemParameter.getId())
                .category(systemParameter.getCategory())
                .key(systemParameter.getKey())
                .value(systemParameter.getValue())
                .description(systemParameter.getDescription())
                .action(new Gson().toJson(actionButtonUtil.get(systemParameter.getId())))
                .build();
    }

    @Override
    public SystemParameterDataTablesVO getDataTablesConfig(Map<String, String> extraConfigs) {

        List<DataTablesColumn> dataTablesColumns = Arrays.asList(
                DataTablesColumn.builder().data("id").title("ID").build(),
                DataTablesColumn.builder().data("category").title("Category").build(),
                DataTablesColumn.builder().data("key").title("Key").build(),
                DataTablesColumn.builder().data("value").title("Value").build(),
                DataTablesColumn.builder().data("description").title("Description").build(),
                DataTablesColumn.builder().data("action").title("Action").orderable(false).searchable(false).build()
        );

        return SystemParameterDataTablesVO.builder()
                .title("System Parameter")
                .dataTablesId("system-parameter")
                .ajaxUrl(contextPath + "/system-parameter/data")
                .dataTablesColumns(dataTablesColumns)
                .extraConfigs(extraConfigs)
                .build();
    }
}
