package com.chanhonlun.basecms.service.impl;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.model.BaseListConfig;
import com.chanhonlun.basecms.model.DefaultListConfig;
import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.repository.SystemParameterRepository;
import com.chanhonlun.basecms.req.datatables.SystemParameterListDataTablesInput;
import com.chanhonlun.basecms.service.SystemParameterService;
import com.chanhonlun.basecms.vo.SystemParameterTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class SystemParameterServiceImpl extends BaseServiceImpl implements SystemParameterService {

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
                .breadcrumbs(breadcrumbUtil.getBreadcrumbs())
                .datatable(systemParameterDataTablesService.getDataTablesConfig(new HashMap<>()))
                .menu(sidebarMenuUtil.getSidebarMenuList())
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
