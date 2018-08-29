package com.chanhonlun.springboottest.service.impl;

import com.chanhonlun.springboottest.constant.MyConstants;
import com.chanhonlun.springboottest.pojo.SystemParameter;
import com.chanhonlun.springboottest.repository.SystemParameterRepository;
import com.chanhonlun.springboottest.req.datatables.SystemParameterListDataTablesInput;
import com.chanhonlun.springboottest.service.SystemParamterService;
import com.chanhonlun.springboottest.vo.BaseListConfig;
import com.chanhonlun.springboottest.vo.DefaultListConfig;
import com.chanhonlun.springboottest.vo.SystemParameterTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class SystemParameterServiceImpl extends BaseServiceImpl implements SystemParamterService {

    @Autowired
    private SystemParameterDataTablesService systemParameterDataTablesService;

    @Autowired
    private SystemParameterRepository systemParameterRepository;

    @Override
    public DataTablesOutput<SystemParameterTableVO> systemParameterDataTablesAPI(SystemParameterListDataTablesInput input) {
        return systemParameterDataTablesService.getDataTablesData(input);
    }

    @Override
    public BaseListConfig getListConfig() {
        return DefaultListConfig.builder()
                .datatable(systemParameterDataTablesService.getDataTablesConfig(new HashMap<>()))
                .build();
    }

    @Override
    public SystemParameter findByIdAndIsDeleteFalse(Long id) {
        return systemParameterRepository.findByIdAndIsDeleteFalse(id);
    }

    @Override
    public SystemParameter softDelete(SystemParameter systemParameter) {
        systemParameter.setUpdatedAt(new Date());
        systemParameter.setUpdatedBy(MyConstants.USER_SYSTEM);
        systemParameter.setIsDelete(true);
        return systemParameterRepository.save(systemParameter);
    }
}
