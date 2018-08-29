package com.chanhonlun.springboottest.service.impl;

import com.chanhonlun.springboottest.constant.ActionButtonType;
import com.chanhonlun.springboottest.model.ActionButton;
import com.chanhonlun.springboottest.pojo.SystemParameter;
import com.chanhonlun.springboottest.repository.SystemParameterRepository;
import com.chanhonlun.springboottest.req.datatables.SystemParameterListDataTablesInput;
import com.chanhonlun.springboottest.service.DataTablesServiceTrait;
import com.chanhonlun.springboottest.model.DataTablesColumn;
import com.chanhonlun.springboottest.vo.SystemParameterDataTablesVO;
import com.chanhonlun.springboottest.vo.SystemParameterTableVO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SystemParameterDataTablesService extends BaseDataTablesService implements
        DataTablesServiceTrait<SystemParameter, Long, SystemParameterTableVO, SystemParameterListDataTablesInput, SystemParameterDataTablesVO> {

    @Autowired
    private SystemParameterRepository systemParameterRepository;

    @Override
    public DataTablesRepository<SystemParameter, Long> getDataTablesRepository() {
        return systemParameterRepository;
    }

    @Override
    public SystemParameterTableVO getTableVOFromPOJO(SystemParameter systemParameter) {

        List<ActionButton> actionButtons = Arrays.asList(
                ActionButton.builder()
                        .type(ActionButtonType.REDIRECT)
                        .displayName("Detail")
                        .bootstrapColor("primary")
                        .href(contextPath + "/system-parameter/" + systemParameter.getId() + "/detail")
                        .build(),
                ActionButton.builder()
                        .type(ActionButtonType.REDIRECT)
                        .displayName("Edit")
                        .bootstrapColor("success")
                        .href(contextPath + "/system-parameter/" + systemParameter.getId() + "/edit")
                        .build(),
                ActionButton.builder()
                        .type(ActionButtonType.DELETE)
                        .displayName("Delete")
                        .bootstrapColor("danger")
                        .href(contextPath + "/system-parameter/" + systemParameter.getId() + "/delete")
                        .build()
        );

        return SystemParameterTableVO.builder()
                .id(systemParameter.getId())
                .category(systemParameter.getCategory())
                .key(systemParameter.getKey())
                .value(systemParameter.getValue())
                .description(systemParameter.getDescription())
                .action(new Gson().toJson(actionButtons))
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
                .ajaxUrl(contextPath + "/system-parameter")
                .dataTablesColumns(dataTablesColumns)
                .extraConfigs(extraConfigs)
                .build();
    }
}
