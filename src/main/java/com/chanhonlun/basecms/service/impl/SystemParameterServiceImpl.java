package com.chanhonlun.basecms.service.impl;

import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.SystemParameterRepository;
import com.chanhonlun.basecms.req.datatables.SystemParameterListDataTablesInput;
import com.chanhonlun.basecms.service.DataTablesServiceTrait;
import com.chanhonlun.basecms.service.SystemParameterService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import com.chanhonlun.basecms.vo.SystemParameterDataTablesVO;
import com.chanhonlun.basecms.vo.SystemParameterTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemParameterServiceImpl extends BaseServiceImpl implements SystemParameterService {

    @Autowired
    private SystemParameterDataTablesService systemParameterDataTablesService;

    @Autowired
    private SystemParameterRepository systemParameterRepository;


    @Override
    public BaseRepository<SystemParameter, Long> getRepository() {
        return systemParameterRepository;
    }

    @Override
    public DataTablesServiceTrait<SystemParameter, Long, SystemParameterTableVO, SystemParameterListDataTablesInput, SystemParameterDataTablesVO> getDataTablesService() {
        return systemParameterDataTablesService;
    }

    @Override
    public BreadcrumbUtil getBreadcrumbUtil() {
        return breadcrumbUtil;
    }

    @Override
    public SidebarMenuUtil getSidebarMenuUtil() {
        return sidebarMenuUtil;
    }
}
