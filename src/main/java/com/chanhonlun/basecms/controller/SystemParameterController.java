package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.req.datatables.SystemParameterListDataTablesInput;
import com.chanhonlun.basecms.service.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.DefaultServiceHasDataTable;
import com.chanhonlun.basecms.service.SystemParameterService;
import com.chanhonlun.basecms.vo.SystemParameterDataTablesVO;
import com.chanhonlun.basecms.vo.SystemParameterTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system-parameter")
public class SystemParameterController extends BaseController implements
        DefaultControllerHasDataTable<SystemParameter, Long, SystemParameterTableVO, SystemParameterListDataTablesInput, SystemParameterDataTablesVO>,
        DefaultControllerHasDeleteActionButton<SystemParameter, Long> {

    @Autowired
    private SystemParameterService systemParameterService;

    @Override
    public DefaultServiceHasDataTable<SystemParameter, Long, SystemParameterTableVO, SystemParameterListDataTablesInput, SystemParameterDataTablesVO> getDefaultPageHasDataTable() {
        return systemParameterService;
    }

    @Override
    public DefaultServiceHasCRUD<SystemParameter, Long> getDefaultPageHasCRUD() {
        return systemParameterService;
    }
}
