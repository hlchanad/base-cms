package com.chanhonlun.basecms.service;

import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.req.datatables.SystemParameterListDataTablesInput;
import com.chanhonlun.basecms.model.BaseListConfig;
import com.chanhonlun.basecms.vo.SystemParameterTableVO;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface SystemParamterService extends BaseService {

    DataTablesOutput<SystemParameterTableVO> systemParameterDataTablesAPI(SystemParameterListDataTablesInput input);

    BaseListConfig getListConfig();

    SystemParameter findByIdAndIsDeleteFalse(Long id);

    SystemParameter softDelete(SystemParameter systemParameter);
}
