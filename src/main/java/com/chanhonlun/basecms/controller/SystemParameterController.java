package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDataTable;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDeleteActionButton;
import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDataTable;
import com.chanhonlun.basecms.service.page.SystemParameterService;
import com.chanhonlun.basecms.vo.row.SystemParameterRowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system-parameter")
public class SystemParameterController extends BaseController implements
        DefaultControllerHasDataTable<SystemParameter, Long, SystemParameterRowVO>,
        DefaultControllerHasDeleteActionButton<SystemParameter, Long> {

    @Autowired
    private SystemParameterService systemParameterService;

    @Override
    public DefaultServiceHasDataTable<SystemParameter, Long, SystemParameterRowVO> getDefaultPageHasDataTable() {
        return systemParameterService;
    }

    @Override
    public DefaultServiceHasCRUD<SystemParameter, Long> getDefaultPageHasCRUD() {
        return systemParameterService;
    }
}
