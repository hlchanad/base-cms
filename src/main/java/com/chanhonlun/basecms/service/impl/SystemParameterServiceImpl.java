package com.chanhonlun.basecms.service.impl;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.model.Breadcrumb;
import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.repository.SystemParameterRepository;
import com.chanhonlun.basecms.req.datatables.SystemParameterListDataTablesInput;
import com.chanhonlun.basecms.service.SystemParameterService;
import com.chanhonlun.basecms.model.BaseListConfig;
import com.chanhonlun.basecms.model.DefaultListConfig;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.vo.SystemParameterTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SystemParameterServiceImpl extends BaseServiceImpl implements SystemParameterService {

    @Autowired
    private SystemParameterDataTablesService systemParameterDataTablesService;

    @Autowired
    private SystemParameterRepository systemParameterRepository;

    @Autowired
    private BreadcrumbUtil breadcrumbUtil;

    @Override
    public DataTablesOutput<SystemParameterTableVO> systemParameterDataTablesAPI(SystemParameterListDataTablesInput input) {
        return systemParameterDataTablesService.getDataTablesData(input);
    }

    @Override
    public BaseListConfig getListConfig() {

        List<Breadcrumb> breadcrumbs = breadcrumbUtil.getBreadcrumbs(Collections.singletonList(
                Breadcrumb.builder()
                        .title("System Parameter")
                        .build()
        ));

        return DefaultListConfig.builder()
                .breadcrumbs(breadcrumbs)
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
