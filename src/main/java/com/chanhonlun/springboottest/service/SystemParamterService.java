package com.chanhonlun.springboottest.service;

import com.chanhonlun.springboottest.pojo.SystemParameter;
import com.chanhonlun.springboottest.req.datatables.SystemParameterListDataTablesInput;
import com.chanhonlun.springboottest.model.BaseListConfig;
import com.chanhonlun.springboottest.vo.SystemParameterTableVO;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface SystemParamterService extends BaseService {

    DataTablesOutput<SystemParameterTableVO> systemParameterDataTablesAPI(SystemParameterListDataTablesInput input);

    BaseListConfig getListConfig();

    SystemParameter findByIdAndIsDeleteFalse(Long id);

    SystemParameter softDelete(SystemParameter systemParameter);
}
