package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.controller.trait.*;
import com.chanhonlun.basecms.form.SystemParameterForm;
import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.response.vo.row.SystemParameterRowVO;
import com.chanhonlun.basecms.service.page.BaseService;
import com.chanhonlun.basecms.service.page.SystemParameterService;
import com.chanhonlun.basecms.service.trait.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system-parameter")
public class SystemParameterController extends BaseController implements
        DefaultControllerHasDataTable<SystemParameter, Long, SystemParameterRowVO>,
        DefaultControllerHasDeleteActionButton<SystemParameter, Long>,
        DefaultControllerHasCreatePage<SystemParameter, Long, SystemParameterForm>,
        DefaultControllerHasEditPage<SystemParameter, Long, SystemParameterForm>,
        DefaultControllerHasDetailPage<SystemParameter, Long> {

    @Autowired
    private SystemParameterService systemParameterService;

    @Override
    protected BaseService getService() {
        return systemParameterService;
    }

    @Override
    public DefaultServiceHasCRUD<SystemParameter, Long> getDefaultPageHasCRUD() {
        return systemParameterService;
    }

    @Override
    public DefaultServiceHasDataTable<SystemParameter, Long, SystemParameterRowVO> getDefaultPageHasDataTable() {
        return systemParameterService;
    }

    @Override
    public DefaultServiceHasEditPage<SystemParameter, Long, SystemParameterForm> getDefaultPageHasEdit() {
        return systemParameterService;
    }

    @Override
    public DefaultServiceHasDetailPage<SystemParameter, Long> getDefaultPageHasDetail() {
        return systemParameterService;
    }

    @Override
    public DefaultServiceHasCreatePage<SystemParameter, Long, SystemParameterForm> getDefaultPageHasCreate() {
        return systemParameterService;
    }
}
