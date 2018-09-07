package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.req.datatables.SystemParameterListDataTablesInput;
import com.chanhonlun.basecms.service.DefaultPageHasCRUD;
import com.chanhonlun.basecms.service.DefaultPageHasDataTable;
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
    public DefaultPageHasDataTable<SystemParameter, Long, SystemParameterTableVO, SystemParameterListDataTablesInput, SystemParameterDataTablesVO> getDefaultPageHasDataTable() {
        return systemParameterService;
    }

    @Override
    public DefaultPageHasCRUD<SystemParameter, Long> getDefaultPageHasCRUD() {
        return systemParameterService;
    }
}
