package com.chanhonlun.basecms.service;

import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.req.datatables.SystemParameterListDataTablesInput;
import com.chanhonlun.basecms.vo.SystemParameterDataTablesVO;
import com.chanhonlun.basecms.vo.SystemParameterTableVO;

public interface SystemParameterService extends
        BaseService,
        DefaultPageHasCRUD<SystemParameter, Long>,
        DefaultPageHasDataTable<SystemParameter, Long, SystemParameterTableVO, SystemParameterListDataTablesInput, SystemParameterDataTablesVO> {

}